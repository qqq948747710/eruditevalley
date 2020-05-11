package controller

import (
	"crypto/rand"
	"encoding/json"
	"fmt"
	"github.com/EruditeValley/web/Entity"
	"github.com/EruditeValley/web/database"
	_ "github.com/EruditeValley/web/memory"
	"github.com/EruditeValley/web/session"
	"io/ioutil"
	"log"
	"mime"
	"net/http"
	"os"
	"path/filepath"
)

const USER_INOF  = "userinfo"
type Applaction struct {
	DBlogic database.DBlogic
}
var GolbalSession *session.Manager
func(app *Applaction)Login(w http.ResponseWriter,r *http.Request){
	query:=r.URL.Query()
	username:=query.Get("username")
	password:=query.Get("password")
	sqldata,err := app.DBlogic.Query("user", "", "where username="+"'"+username+"'"+" and password='"+password+"'")
	if err != nil {
		fmt.Println(err)
	}
	if len(sqldata)==0{
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	user:=Entity.UserEntity{Code:0,Name:sqldata[0]["name"],Username:sqldata[0]["username"],Headpath:sqldata[0]["headpath"],Email:sqldata[0]["email"],Sex:sqldata[0]["sex"]}
	session:=GolbalSession.SessionStart(w,r)
	session.Set(USER_INOF,user)
	ShowJson(w,r,user)
}


func(app *Applaction)Register(w http.ResponseWriter,r *http.Request){
	query:=r.URL.Query()
	name:=query.Get("name")
	username:=query.Get("username")
	password:=query.Get("password")
	repwd:=query.Get("repassword")
	if password!=repwd{
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return;
	}
	value,err:=app.DBlogic.Query("user","username","where username="+"'"+username+"'")
	if err!=nil{
		fmt.Println(err)
	}
	if len(value)!=0{
		user:=Entity.UserEntity{Code:2}
		ShowJson(w,r,user)
		return
	}
	user:= map[string]string{
		"name":name,
		"username":username,
		"password":password,
	}
	app.DBlogic.Insert("user",user)
	resuser:=Entity.UserEntity{Code:0,Username:username,Name:name,Headpath:"",Email:"",Sex:""}
	ShowJson(w,r,resuser)
}

func(app *Applaction)GetUserInfo(w http.ResponseWriter,r *http.Request){
	islogin,userinfo:= Islogin(w, r)
	if !islogin{
		entity:=Entity.UserEntity{Code:1}
		ShowJson(w,r,entity)
		return;
	}
	ShowJson(w,r,userinfo)
}

func(app *Applaction)UpLoadHead(w http.ResponseWriter,r *http.Request){
	start:="{"
	content:=""
	end:="}"
	islogin,userinfo:=Islogin(w,r)
	if !islogin{
		content="\"error\":1,\"result\":{\"msg\":\"you is not login!\",\"path\":\"\"}"
		w.Write([]byte(start+content+end))
		return
	}
	if r.Method!="POST"{
		content="\"error\":1,\"result\":{\"msg\":\"you is not post!\",\"path\":\"\"}"
		w.Write([]byte(start+content+end))
		return
	}
	file,_,err:=r.FormFile("file")
	defer file.Close()
	if err != nil {
		content="\"error\":1,\"result\":{\"msg\":\"文件上传失败!\",\"path\":\"\"}"
		w.Write([]byte(start+content+end))
		return
	}
	fileBytes,err:=ioutil.ReadAll(file)
	if err != nil {
		content="\"error\":1,\"result\":{\"msg\":\"文件读取失败!\",\"path\":\"\"}"
		w.Write([]byte(start+content+end))
		return
	}
	if len(fileBytes)>1000000{
		content="\"error\":1,\"result\":{\"msg\":\"文件太大!\",\"path\":\"\"}"
		w.Write([]byte(start+content+end))
		return
	}
	filetype:=http.DetectContentType(fileBytes)
	switch filetype {
	case "image/jpeg", "image/jpg":
	case "image/gif", "image/png":
	case "application/pdf":
		break
	default:
		content = "\"error\":1,\"result\":{\"msg\":\"文件类型错误\",\"path\":\"\"}"
		w.Write([]byte(start + content + end))
		return
	}
	fileName:=randToken(12)
	fileEndings,err:=mime.ExtensionsByType(filetype)
	newPath:=filepath.Join("web","static","images","head",fileName+fileEndings[0])
	newFile,err:=os.Create(newPath)
	if err != nil {
		fmt.Println("创建文件失败：" + err.Error())
		content = "\"error\":1,\"result\":{\"msg\":\"创建文件失败\",\"path\":\"\"}"
		w.Write([]byte(start + content + end))
		return
	}
	defer newFile.Close()
	if _, err := newFile.Write(fileBytes); err != nil || newFile.Close() != nil {
		log.Println("写入文件失败：" + err.Error())
		content = "\"error\":1,\"result\":{\"msg\":\"保存文件内容失败\",\"path\":\"\"}"
		w.Write([]byte(start + content + end))
		return
	}
	path := "/static/images/head/" + fileName + fileEndings[0]
	content = "\"error\":0,\"result\":{\"fileType\":\"image/png\",\"path\":\"" + path + "\",\"fileName\":\""+fileName + fileEndings[0]+"\"}"
	w.Write([]byte(start + content + end))
	userentity:= userinfo.(Entity.UserEntity)
	data:= map[string]string{
		"headpath":path,
	}
	_,err=app.DBlogic.Update("user",data,"where username="+"'"+userentity.Username+"'")
	if err!=nil{
		fmt.Println(err)
	}
	userentity.Headpath=path
	session:=GolbalSession.SessionStart(w,r)
	session.Set(USER_INOF,userentity)
}

func(app *Applaction) AlterProfile(w http.ResponseWriter,r *http.Request){
	islogin,userinfo:=Islogin(w,r)
	if !islogin{
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	defer r.Body.Close()
	body,err:=ioutil.ReadAll(r.Body)
	if err != nil {
		fmt.Println("request body read is err!!!!",err)
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	userentity:=userinfo.(Entity.UserEntity)
	alertuser:=Entity.UserEntity{}
	err=json.Unmarshal(body,&alertuser)
	if err != nil {
		fmt.Println("request body json err",err)
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	data:=map[string]string{
		"name":alertuser.Name,
		"email":alertuser.Email,
		"sex":alertuser.Sex,
	}
	_,err=app.DBlogic.Update("user",data,"where username="+"'"+userentity.Username+"'")
	if err != nil {
		fmt.Println("sql error",err)
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	user:=Entity.UserEntity{Code:0,Username:userentity.Username,Name:data["name"],Email:data["email"],Sex:data["sex"],Headpath:userentity.Headpath}
	session:=GolbalSession.SessionStart(w,r)
	session.Set(USER_INOF,user)
	ShowJson(w,r,user)
}


func randToken(len int)string{
	b:=make([]byte,len)
	rand.Read(b)
	return fmt.Sprintf("%x",b)
}

func init(){
	GolbalSession,_=session.NewManager("memory","gosessionid",3600)
	GolbalSession.GC()
}