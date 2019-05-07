package com.example.apiintegrationtask.datasource.models



data class Base(val hits: List<Hits>?, val nbHits: Number?, val page: Number?, val nbPages: Number?, val hitsPerPage: Number?, val processingTimeMS: Number?, val exhaustiveNbHits: Boolean?, val query: String?, val params: String?)
data class Author(val value: String?, val matchLevel: String?, val matchedWords: List<Any>?)
data class Hits(val created_at: String?, val title: String?, val url: String?, val author: String?, val points: Number?, val story_text: Any?, val comment_text: Any?, val num_comments: Number?, val story_id: Any?, val story_title: Any?, val story_url: Any?, val parent_id: Any?, val created_at_i: Number?, val relevancy_score: Number?, val _tags: List<String>?, val objectID: String?, val _highlightResult: _highlightResult?)

data class Title(val value: String?, val matchLevel: String?, val matchedWords: List<Any>?)

data class Url(val value: String?, val matchLevel: String?, val matchedWords: List<Any>?)

data class _highlightResult(val title: Title?, val url: Url?, val author: Author?)