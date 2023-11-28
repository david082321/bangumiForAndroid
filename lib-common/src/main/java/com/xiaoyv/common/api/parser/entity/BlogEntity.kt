package com.xiaoyv.common.api.parser.entity

/**
 * Class: [BlogEntity]
 *
 * @author why
 * @since 11/28/23
 */
data class BlogEntity(
    var id: String = "",
    var title: String = "",
    var image: String = "",
    var timeline: CharSequence = "",
    var time: String = "",
    var content: String = "",
    var comment: String = "",
    var mediaType: String = ""
)
