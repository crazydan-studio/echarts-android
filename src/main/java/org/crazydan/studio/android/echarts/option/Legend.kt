package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 * [说明文档](https://echarts.apache.org/en/option.html#legend)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class Legend : JSONable {
    private val holder = LegendHolder()

    enum class Type() {
        Plain, Scroll
    }

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 图例类型 */
    fun type(block: LegendType.() -> Unit) {
        LegendType(holder).apply(block)
    }

    /** 文本格式化器 */
    fun formatter(value: String) {
        holder.formatter = value
    }

    /** 与容器周边的间隔 */
    fun margin(block: Margin.() -> Unit) {
        Margin(holder).apply(block)
    }
}

@EChartsOption
class LegendType(
    val holder: LegendHolder,
) {

    /** 普通类型：平铺展开图例 */
    fun plain() {
        holder.type = Legend.Type.Plain
    }

    /** 滚动翻页类型。当图例数量较多时可以使用 */
    fun scroll() {
        holder.type = Legend.Type.Scroll
    }
}

data class LegendHolder(
    var show: Boolean = true,
    var type: Legend.Type = Legend.Type.Plain,

    var formatter: String? = null,
) : MarginHolder()
