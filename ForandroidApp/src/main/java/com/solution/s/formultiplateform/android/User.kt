package com.solution.s.formultiplateform.android

class User {
    // getter methods for all variables.
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var password: String? = null
    var bio: String? = null
    var image: String? = null

    constructor(email: String?, password: String?) {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    constructor(
        Name: String?,
        Email: String?,
        Phone: String?,
        Password: String?,
        Bio: String?,
        Image: String?
    ) {
        name = Name
        email = Email
        phone = Phone
        password = Password
        bio = Bio
        image = Image
    }

}
