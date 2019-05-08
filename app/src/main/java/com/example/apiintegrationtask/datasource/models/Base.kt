package com.example.apiintegrationtask.datasource.models



data class Base(val hits: List<Hits>?, val nbHits: Number?, val page: Number?, val nbPages: String?, val hitsPerPage: Number?, val processingTimeMS: Number?, val exhaustiveNbHits: Boolean?, val query: String?, val params: String?)
data class Author(val value: String?, val matchLevel: String?, val matchedWords: List<String>?)
data class Hits(val created_at: String?, val title: String?, val url: String?, val author: String?, val points: Number?, val story_text: String?, val comment_text: String?, val num_comments: Number?, val story_id: String?, val story_title: String?, val story_url: String?, val parent_id: String?, val created_at_i: Number?, val relevancy_score: Number?, val _tags: List<String>?, val objectID: String?, val _highlightResult: _highlightResult?)

data class Title(val value: String?, val matchLevel: String?, val matchedWords: List<String>?)

data class Url(val value: String?, val matchLevel: String?, val matchedWords: List<String>?)

data class _highlightResult(val title: Title?, val url: Url?, val author: Author?)