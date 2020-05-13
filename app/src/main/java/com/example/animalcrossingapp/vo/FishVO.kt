package com.example.animalcrossingapp.vo

class FishVO {
    constructor()

    constructor(name_japan: String, price: Int, catch_flag: String) {
        this.name_japan = name_japan
        this.price = price
        this.catch_flag = catch_flag
    }

    var name_japan: String = ""
    var name_korea: String = ""
    var condition_code: String= ""
    var habitat_code: String = ""
    var capture_code: String = ""
    var price: Int = 0
    var catch_flag: String = ""
    var rare_degree: String = ""
    var size: String = ""
    var sortation: String = ""
    var month: String = ""
    var time: String = ""

}