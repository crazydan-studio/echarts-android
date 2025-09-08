package org.crazydan.studio.android.echarts

import org.crazydan.studio.android.echarts.EChartsOptions.Companion.toJSON
import kotlin.reflect.KClass

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
interface EChartsOption : JSONable

interface EChartsOptions : JSONable {

    /** 将 [option] 的属性及属性值合并到 [toJSON] 结果的根节点上 */
    fun add(option: EChartsOption): EChartsOptions

    /** 将多个同类型的 [option] 的属性及属性值合并到 [toJSON] 结果的根节点上，并将同名属性的值组装为数组 */
    fun multiAdd(option: EChartsOption): EChartsOptions

    companion object : EChartsOptions {

        override fun add(option: EChartsOption): EChartsOptions =
            EChartsOptionsDefault().add(option)

        override fun multiAdd(option: EChartsOption): EChartsOptions =
            EChartsOptionsDefault().multiAdd(option)

        override fun toJSON(): String = "{}"
    }
}

private class EChartsOptionsDefault() : EChartsOptions {
    private val map = mutableMapOf<KClass<*>, EChartsOption>()
    private val multiMap = mutableMapOf<KClass<*>, MutableList<EChartsOption>>()

    override fun add(option: EChartsOption): EChartsOptions {
        map.put(option::class, option)
        return this
    }

    override fun multiAdd(option: EChartsOption): EChartsOptions {
        multiMap.computeIfAbsent(option::class) { mutableListOf() }.add(option)
        return this
    }

    override fun toJSON(): String {
        val objMap = mutableMapOf<Any, Any>()
        map.forEach { entry ->
            val m = objToMap(entry.value)
            objMap.putAll(m)
        }

        val listMap = mutableMapOf<Any, MutableList<Any>>()
        multiMap.forEach { entry ->
            entry.value.forEach { option ->
                objToMap(option).forEach { e ->
                    listMap.computeIfAbsent(e.key) {
                        mutableListOf()
                    }
                        .add(e.value)
                }
            }
        }
        objMap.putAll(listMap)

        return mapToJSON(objMap)
    }
}