package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.Legend.Type

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
    fun type(block: LegendTypeScope.() -> Type) {
        holder.type = LegendTypeScope().block()
    }

    /** 文本格式化器 */
    fun formatter(value: String) {
        holder.formatter = value
    }

    /** 与容器周边的间隔 */
    fun margin(block: Margin.() -> Unit) {
        Margin(holder).block()
    }
}

data class LegendHolder(
    var show: Boolean = true,
    var type: Type = Type.Plain,

    var formatter: String? = null,
) : MarginHolder()

class LegendTypeScope() {

    /** 普通类型：平铺展开图例 */
    val plain = Type.Plain

    /** 滚动翻页类型。当图例数量较多时可以使用 */
    val scroll = Type.Scroll
}
