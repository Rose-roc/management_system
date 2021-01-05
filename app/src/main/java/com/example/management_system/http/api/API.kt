package com.example.management_system.http.api

object API {

    const val BASE_URL = "http://192.168.31.135:8088/"

    const val LOGIN = "login"
    const val REGISTER = "register"
    const val UPDATE_ADMIN = "updateadmin"

    const val GET_OWNERS = "owners"
    const val ADD_OWNER = "addowner"
    const val DELETE_OWNER = "deleteowner"
    const val GET_OWNERS_BY_NAME = "ownersbyname"
    const val UPDATE_OWNER = "updateowner"

    const val GET_HOUSES = "findHouseByPage"
    const val GET_HOUSE_BY_ID = "findHouseById"
    const val UPDATE_HOUSE_BY_ID = "updateHouseById"
    const val FIND_HOUSE_TYPE_LIST = "findHouseTypeList"

    const val FIND_FAMILY_BY_ID = "findFamilyById"

    const val GET_MAINTAIN_LIST = "findMaintainList"
    const val DELETE_MAINTAIN_BY_ID = "deleteMaintainById"

    const val GET_PAY_LIST = "findPayList"
    const val DELETE_PAY_BY_ID = "deletePayById"

    const val FIND_PAYMENT_ITEM_LIST = "findPaymentItemList"
    const val DELETE_PAYMENT_ITEM_BY_NAME = "deletePaymentItemByName"
    const val UPDATE_PAYMENT_ITEM = "updatePaymentItem"
    const val INSERT_PAYMENT_ITEM = "insertPaymentItem"
}