package com.sunny.learn.pcfassignmentapp

import java.io.Serializable

class Result(
    val id: Int,
    val name: String,
    val full_name: String,
    val description: String,
    val owner: Owner
) : Serializable

class Owner(
    val avatar_url : String,
    val html_url : String
) : Serializable