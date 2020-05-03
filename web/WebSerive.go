package web

import (
	"fmt"
	"github.com/EruditeValley/web/controller"
	"net/http"
)

func WebStart(app *controller.Applaction){
	fs:=http.FileServer(http.Dir("web/static"))
	http.Handle("/static/",http.StripPrefix("/static/",fs))
	http.HandleFunc("/login",app.Login)
	http.HandleFunc("/register",app.Register)
	http.HandleFunc("/getuserinfo",app.GetUserInfo)
	err:=http.ListenAndServe("0.0.0.0:3000",nil)
	if err != nil {
		fmt.Println("web启动失败",err)
	}
}