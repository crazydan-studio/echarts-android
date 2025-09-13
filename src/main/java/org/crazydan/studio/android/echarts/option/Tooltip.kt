package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.AxisPointer.Type
import org.crazydan.studio.android.echarts.option.Tooltip.TriggerType

/**
 * [说明文档](https://echarts.apache.org/en/option.html#tooltip)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class Tooltip : JSONable {
    private val holder = TooltipHolder()

    enum class TriggerType() {
        Item, Axis
    }

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 触发方式 */
    fun triggerBy(block: TriggerTypeScope.() -> TriggerType) {
        holder.trigger = block.invoke(TriggerTypeScope())
    }

    /** [坐标轴指示器配置](https://echarts.apache.org/en/option.html#tooltip.axisPointer) */
    fun axisPointer(block: AxisPointer.() -> Unit) {
        holder.axisPointer = AxisPointer().apply(block)
    }
}

/**
 * [说明文档](https://echarts.apache.org/en/option.html#tooltip.axisPointer)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class AxisPointer : JSONable {
    private val holder = AxisPointerHolder()

    override fun toJSON(): String = holder.toJSON()

    enum class Type() {
        Line, Cross, Shadow
    }

    /** 指示器类型 */
    fun type(block: AxisPointerTypeScope.() -> Type) {
        holder.type = AxisPointerTypeScope().block()
    }

    /** [文本标签配置](https://echarts.apache.org/zh/option.html#tooltip.axisPointer.label) */
    fun label(block: Label.() -> Unit) {
        holder.label = Label().apply(block)
    }
}

data class TooltipHolder(
    var show: Boolean = true,

    var trigger: TriggerType = TriggerType.Item,
    var axisPointer: AxisPointer? = null,

    var formatter: String? = null,
) : JSONable

data class AxisPointerHolder(
    var type: Type? = null,
    var label: Label? = null,
) : JSONable

class TriggerTypeScope {
    /** 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。默认为该触发方式 */
    val item = TriggerType.Item

    /** 坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用 */
    val axis = TriggerType.Axis
}

class AxisPointerTypeScope {
    /** 直线指示器：与 x 轴垂直的虚线 */
    val line = Type.Line

    /** 十字准星指示器：与 x、y 轴垂直的两条虚线 */
    val cross = Type.Cross

    /** 阴影指示器：与 x 轴垂直的矩形阴影，在数据图形之下 */
    val shadow = Type.Shadow
}