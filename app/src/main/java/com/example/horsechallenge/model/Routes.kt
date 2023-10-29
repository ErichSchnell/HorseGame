package com.example.horsechallenge.model

sealed class Routes(val route:String) {
    object Game:Routes("sc_game")
    object PayPremium:Routes("sc_payPremium")
    object SelectLvl:Routes("sc_selectLvl")
//    object PayPremium:Routes("sc_payPremium/{state}"){
//        fun createRoute(state: Boolean) = "sc_payPremium/$state"
//    }
}