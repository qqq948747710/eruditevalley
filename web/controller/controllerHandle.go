package controller

import (
	"fmt"
	"github.com/EruditeValley/web/Entity"
	"github.com/EruditeValley/web/database"
	_ "github.com/EruditeValley/web/memory"
	"github.com/EruditeValley/web/session"
	"net/http"
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
	sqldata,err := app.DBlogic.Query("user", "name,username", "where username="+"'"+username+"'"+" and password='"+password+"'")
	if err != nil {
		fmt.Println(err)
	}
	if len(sqldata)==0{
		user:=Entity.UserEntity{Code:1}
		ShowJson(w,r,user)
		return
	}
	user:=Entity.UserEntity{Code:0,Name:sqldata[0]["name"],Username:sqldata[0]["username"],Headpath:""}
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
	resuser:=Entity.UserEntity{Code:0,Username:username,Name:name,Headpath:""}
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

func init(){
	GolbalSession,_=session.NewManager("memory","gosessionid",3600)
	GolbalSession.GC()
}