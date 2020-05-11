package Entity

type UserEntity struct {
	Code int `json:"code"`
	Username string `json:"username"`
	Name string `json:"name"`
	Email string `json:"email"`
	Sex string `json:"sex"`
	Headpath string `json:"headpath"`
}
