package controller

import (
	"net/http"
)


func Islogin(w http.ResponseWriter,r *http.Request)(bool,interface{}){
	session:= GolbalSession.SessionStart(w,r)
	if userinfo:= session.Get(USER_INOF);userinfo!=nil{
		return true,userinfo
	}
	return false,nil
}