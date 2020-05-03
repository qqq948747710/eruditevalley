package controller

import (
	"encoding/json"
	"fmt"
	"net/http"
)

func ShowJson(w http.ResponseWriter,r *http.Request,entity interface{}){
	dara,err:=json.Marshal(entity);
	if err != nil {
		fmt.Println("模板显示错误，json解析失败")
	}
	_,err=w.Write(dara)
	if err != nil {
		fmt.Println("模板显示错误返回失败")
	}

}