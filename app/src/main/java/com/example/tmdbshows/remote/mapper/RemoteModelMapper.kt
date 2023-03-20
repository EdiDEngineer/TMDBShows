package com.example.tmdbshows.remote.mapper

interface RemoteModelMapper<in M, out E> {

    fun mapFromModel(model: M): E

    fun mapModelList(models: List<M>?): List<E> {
        val list = mutableListOf<E>()
        models?.forEach {
            list.add(mapFromModel(it))
        }

        return list
    }

    fun safeString(string: String?, prependIfNonNull: String = ""): String {
        return string?.let {
            buildString {
                append(prependIfNonNull)
                append(it)
            }
        } ?: "N/A"
    }
}