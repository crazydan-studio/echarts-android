package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-09
 */
@EChartsOption
class Label : JSONable {
    private val holder = LabelHolder()

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 文本格式化器 */
    fun formatter(value: String) {
        holder.formatter = value
    }
}

private data class LabelHolder(
    var show: Boolean = true,
    var formatter: String? = null,
) : JSONable
