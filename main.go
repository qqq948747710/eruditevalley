package main

import (
	"fmt"
	"github.com/EruditeValley/web"
	"github.com/EruditeValley/web/controller"
	"github.com/EruditeValley/web/database"
)

func main()  {
	db,err:=database.Connesql()
	if err!=nil{
		fmt.Println(err)
	}
	dblogic:=database.DBlogic{DB:db}
	app:=&controller.Applaction{DBlogic:dblogic}
	web.WebStart(app)
}