package com.mmm.exam_android

class ShoppingModel {

    lateinit var key: String
    lateinit var title: String
    lateinit var description: String
    lateinit var category: String
    lateinit var price: String
    lateinit var image: String

    constructor()

    constructor(
        key: String,
        title: String,
        description: String,
        category: String,
        price: String,
        image: String,

        ) {
        this.key = key
        this.title = title
        this.description = description
        this.category = category
        this.price = price
        this.image = image

    }
}

