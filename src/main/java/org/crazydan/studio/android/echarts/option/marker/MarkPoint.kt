package org.crazydan.studio.android.echarts.option.marker

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-12
 */
@EChartsOption
class MarkPoint() : JSONable {
    private val holder = MarkPointHolder()

    override fun toJSON(): String = holder.toJSON()

    /** 图形是否不响应和触发鼠标事件 */
    fun silent(value: Boolean) {
        holder.silent = value
    }

    /** 数据点配置 */
    fun byData(block: MarkDataPointWithLabel.() -> Unit) {
        val p = MarkDataPointHolder()
        MarkDataPointWithLabel(p).block()

        holder.data.add(p)
    }
}

@EChartsOption
class MarkDataPointWithLabel(
    private val holder: MarkDataPointHolder,
) : MarkDataPointWithSymbol(holder) {

    /** 名字 */
    fun name(value: String) {
        holder.name = value
    }

    /** 标签配置 */
    fun label(block: MarkPointLabel.() -> Unit) {
        holder.label = MarkPointLabel().apply(block)
    }
}

private class MarkPointHolder(
    var silent: Boolean = true,

    val data: MutableList<MarkDataPointHolder> = mutableListOf(),
) : MarkWithSymbolHolder()