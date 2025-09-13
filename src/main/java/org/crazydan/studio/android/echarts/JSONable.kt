package org.crazydan.studio.android.echarts

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
interface JSONable {
    fun toJSON(): String = objToJSON(this)
}

fun objToJSON(obj: Any): String =
    mapToJSON(objToMap(obj) as Map<Any, Any?>)

fun mapToJSON(map: Map<Any, Any?>): String =
    map.map { entry -> jsonKeyValue(entry.key, entry.value) }
        .filter { it != null }
        .joinToString(",", "{", "}")

fun listToJSON(list: Collection<*>): String =
    jsonValue(list)

fun enumToJSON(enum: Enum<*>): String {
    val name = enum.name.replaceFirstChar { it.lowercase() }
    return "\"$name\""
}

fun objToMap(obj: Any): Map<String, Any> =
    obj::class.memberProperties
        .map { prop ->
            val value = getPropValue(prop, obj)
            if (value == null) {
                null
            } else {
                prop.name to value
            }
        }
        .filter { it != null }
        .associate { it as Pair<String, Any> }

private fun getPropValue(prop: KProperty1<out Any, *>, obj: Any): Any? {
    prop.isAccessible = true
    return prop.javaField?.get(obj)
}

private fun jsonKeyValue(key: Any?, value: Any?): String? {
    if (key == null || value == null) {
        return null
    }

    val json = jsonValue(value)

    return "\"$key\": $json"
}

private fun jsonValue(value: Any?): String {
    return when (value) {
        null -> {
            "null"
        }

        is JSONable -> {
            value.toJSON()
        }

        is Number, is Boolean -> {
            value.toString()
        }

        is Enum<*> -> {
            enumToJSON(value)
        }

        is Map<*, *> -> {
            mapToJSON(value as Map<Any, Any?>)
        }

        is Collection<*> -> {
            value.joinToString(",", "[", "]") { jsonValue(it) }
        }

        is Array<*> -> {
            value.joinToString(",", "[", "]") { jsonValue(it) }
        }

        else -> {
            val str =
                value.toString()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\\\n")
            "\"$str\""
        }
    }
}