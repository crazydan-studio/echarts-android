package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */

/** 像素值 */
inline val Int.pixel: Size
    get() = Size.Pixel(this)

/** 百分比 */
inline val Float.percent: Size
    get() = Size.Percent(this)

@EChartsOption
class Margin(
    private val holder: MarginHolder,
) {

    /** 左侧的间距 */
    fun left(value: Size) {
        holder.left = value
    }

    /** 右侧的间距 */
    fun right(value: Size) {
        holder.right = value
    }

    /** 顶部的间距 */
    fun top(value: Size) {
        holder.top = value
    }

    /** 底部的间距 */
    fun bottom(value: Size) {
        holder.bottom = value
    }
}

interface Size : JSONable {

    class Pixel(val value: Int) : Size {
        override fun toJSON(): String {
            return value.toString()
        }
    }

    class Percent(val value: Float) : Size {
        override fun toJSON(): String {
            return "\"$value%\""
        }
    }
}

open class MarginHolder(
    var left: Size? = null,
    var right: Size? = null,
    var top: Size? = null,
    var bottom: Size? = null,
) : JSONable