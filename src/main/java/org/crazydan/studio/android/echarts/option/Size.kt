package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 * 间距
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class Margin(
    private val holder: MarginHolder,
) : SizeScope() {

    /** 左侧的间距 */
    fun left(value: Size) {
        holder.left = value
    }

    /** 右侧的间距 */
    fun right(value: Size) {
        holder.right = value
    }

    /** 左右两侧的间距 */
    fun horizontal(value: Size) {
        left(value)
        right(value)
    }

    /** 顶部的间距 */
    fun top(value: Size) {
        holder.top = value
    }

    /** 底部的间距 */
    fun bottom(value: Size) {
        holder.bottom = value
    }

    /** 上下两侧的间距 */
    fun vertical(value: Size) {
        top(value)
        bottom(value)
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

open class SizeScope {

    /** 像素值 */
    inline val Int.px: Size.Pixel
        get() = Size.Pixel(this)

    /** 百分比 */
    inline val Float.pct: Size.Percent
        get() = Size.Percent(this)
}

open class MarginHolder(
    var left: Size? = null,
    var right: Size? = null,
    var top: Size? = null,
    var bottom: Size? = null,
) : JSONable