package com.mokhamadwijaya.oskop.penjualan.model

import com.google.gson.annotations.SerializedName

class DashboardResponse {
    @SerializedName("username")
    var userName: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("jumlahpesanan")
    var jumlahPesanan: Int? = null
    @SerializedName("totalpesanan")
    var totalPesanan: Float? = null
    @SerializedName("omset")
    var omset: Float? = null
    @SerializedName("pesanans")
    var pesanans = ArrayList<Pesanan>()
}

class Pesanan {
    @SerializedName("pelanggan")
    var pelanggan: String? = null
    @SerializedName("alamat")
    var alamat: String? = null
    @SerializedName("total")
    var total: String? = null
    @SerializedName("displayable_lunas")
    var pelunasan: String? = null
}

class LoginResponse {
    @SerializedName("token_type")
    var tokenType: String? = null
    @SerializedName("expires_in")
    var expiresIn: String? = null
    @SerializedName("access_token")
    var accessToken: String? = null
    @SerializedName("refresh_token")
    var refreshToken: String? = null
}

data class LoginBody(val username: String,
                     val password: String)