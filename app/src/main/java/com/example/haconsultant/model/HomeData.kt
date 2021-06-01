package com.example.haconsultant.model

object HomeData {
    fun getProduct() = listOf(
        Product(
            name = "Смартфон Apple iPhone XR 128GB Black",
            codeVendor = "2220001",
            imageUrl = "https://static.eldorado.ru/photos/71/715/665/96/new_71566596_l_1605093913.jpeg",
            prices = 2000,
            evaluation = 4.3F,
            sizeReviews = 23,
            weight = 2.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089603.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089623.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089643.jpeg/resize/380x240/"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Игровая приставка Sony PlayStation 5",
            codeVendor = "2220002",
            imageUrl = "https://static.eldorado.ru/photos/71/715/399/84/new_71539984_l_1600344297.jpeg",
            prices = 50000,
            evaluation = 2.3F,
            sizeReviews = 2,
            weight = 5.2F,
            listImage = listOf(
                "https://img.mvideo.ru/Pdb/40073270b.jpg",
                "https://img.mvideo.ru/Pdb/40073270b1.jpg",
                "https://img.mvideo.ru/Pdb/40073270b2.jpg",
                "https://img.mvideo.ru/Pdb/40073270b3.jpg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Беспроводные наушники с микрофоном Apple AirPods (2019)",
            codeVendor = "2220003",
            imageUrl = "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554719994.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 0,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554719994.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720014.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720096.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720112.jpeg/resize/380x240/"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Название 4",
            codeVendor = "2220004",
            imageUrl = "https://static.eldorado.ru/photos/71/713/748/92/new_71374892_l_1529342853.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 0,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/713/748/92/new_71374892_l_1529342853.jpeg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Название 5",
            codeVendor = "2220005",
            imageUrl = "https://static.eldorado.ru/photos/71/715/908/88/new_71590888_l_1616156634.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 2,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/908/88/new_71590888_l_1616156634.jpeg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        )
    )


    fun getHomeCatalog() = listOf(
        HomeCatalog(name = "Каталог", imageUrl = null),
        HomeCatalog(
            name = "Телефоны",
            imageUrl = "https://static.eldorado.ru/upload/iblock/a1d/a1d7d0dae29421fc2d6f799017b6650c.svg"
        ),
        HomeCatalog(
            name = "Телевизоры",
            imageUrl = "https://static.eldorado.ru/upload/iblock/b79/b79cfc58cff50753ea789cf605b0675e.svg"
        ),
        HomeCatalog(
            name = "Роутеры",
            imageUrl = "https://static.eldorado.ru/upload/iblock/b4c/b4c999d5ef5af2aa67ea3c6a57582356.svg"
        ),
        HomeCatalog(
            name = "Периферия",
            imageUrl = "https://static.eldorado.ru/upload/iblock/d23/d23d781613d5d694a4adadce5c081b3e.svg"
        ),
        HomeCatalog(
            name = "Фото",
            imageUrl = "https://static.eldorado.ru/upload/iblock/68c/68c7aefc4fb5bba41e5b22afb4db5e8d.svg"
        )
    )

    fun getMutableProduct(): MutableList<Product> = mutableListOf(
        Product(
            name = "Смартфон Apple iPhone XR 128GB Black",
            codeVendor = "2220001",
            imageUrl = "https://static.eldorado.ru/photos/71/715/665/96/new_71566596_l_1605093913.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 2,
            weight = 2.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089603.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089623.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089643.jpeg/resize/380x240/"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Игровая приставка Sony PlayStation 5",
            codeVendor = "2220002",
            imageUrl = "https://static.eldorado.ru/photos/71/715/399/84/new_71539984_l_1600344297.jpeg",
            prices = 50000,
            evaluation = 2.3F,
            sizeReviews = 2,
            weight = 5.2F,
            listImage = listOf(
                "https://img.mvideo.ru/Pdb/40073270b.jpg",
                "https://img.mvideo.ru/Pdb/40073270b1.jpg",
                "https://img.mvideo.ru/Pdb/40073270b2.jpg",
                "https://img.mvideo.ru/Pdb/40073270b3.jpg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Беспроводные наушники с микрофоном Apple AirPods (2019)",
            codeVendor = "2220003",
            imageUrl = "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554719994.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 0,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554719994.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720014.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720096.jpeg/resize/380x240/",
                "https://static.eldorado.ru/photos/71/715/117/62/new_71511762_l_1554720112.jpeg/resize/380x240/"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Название 4",
            codeVendor = "2220004",
            imageUrl = "https://static.eldorado.ru/photos/71/713/748/92/new_71374892_l_1529342853.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 0,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/713/748/92/new_71374892_l_1529342853.jpeg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        ),
        Product(
            name = "Название 5",
            codeVendor = "2220005",
            imageUrl = "https://static.eldorado.ru/photos/71/715/908/88/new_71590888_l_1616156634.jpeg",
            prices = 2000,
            evaluation = 2.3F,
            sizeReviews = 2,
            weight = 5.2F,
            listImage = listOf(
                "https://static.eldorado.ru/photos/71/715/908/88/new_71590888_l_1616156634.jpeg"
            ),
            description = "описание",
            listProduct = listOf(),
            manufacturer = "apple",
            characteristics = mapOf()
        )
    )

    fun getbasketItem(): MutableList<BasketItem> {
        val mutableList = mutableListOf<BasketItem>()
        for (i in getMutableProduct()) {
            mutableList.add(BasketItem(i, 1))
        }
        return mutableList
    }

    fun getLinsPage() = listOf<String>(
        "https://static.eldorado.ru/photos/71/715/665/96/new_71566596_l_1605093913.jpeg",
        "https://static.eldorado.ru/photos/71/715/908/88/new_71590888_l_1616156634.jpeg",
        "https://static.eldorado.ru/photos/71/713/748/92/new_71374892_l_1529342853.jpeg"
    )

    fun getCatalog() =
        Catalog(
            "Каталог", listOf(
                Catalog(
                    "Телефоны",
                    listOf<Catalog>(
                        Catalog("Смартфоны", null),
                        Catalog("Кнопочные", null),
                        Catalog("Домашние", null)
                    )
                ),
                Catalog(
                    "Телевизоры",
                    listOf<Catalog>(
                        Catalog("Телевизоры", null),
                        Catalog("Приставки", null),
                        Catalog("DVD, Blu-Ray и медиаплееры", null)
                    )
                ),
                Catalog(
                    "Роутеры",
                    listOf<Catalog>(
                        Catalog("Приемники", null),
                        Catalog("Приставки Wi-fi", null),
                        Catalog("Коммутаторы", null)
                    )
                ),
                Catalog(
                    "Роутеры",
                    listOf<Catalog>(
                        Catalog("Приемники", null),
                        Catalog("Приставки Wi-fi", null),
                        Catalog("Коммутаторы", null)
                    )
                )
            )
        )
}