package model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*json
  {"msg":"ورود با موفقیت انجام شد","status":true}*/
data class LoginModel(
    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("status")
    @Expose
    var status: Boolean = false
)