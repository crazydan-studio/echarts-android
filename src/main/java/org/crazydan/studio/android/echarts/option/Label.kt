package org.crazydan.studio.android.echarts.option

import androidx.annotation.FloatRange
import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-09
 */
@EChartsOption
open class Label(
    private val holder: LabelHolder = LabelHolder(),
) : JSONable {

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 文本格式化器 */
    fun formatter(value: String) {
        holder.formatter = value
    }

    /** 旋转角度 */
    fun rotate(@FloatRange(from = -90.0, to = 90.0) value: Float) {
        holder.rotate = value
    }
}

open class LabelHolder(
    var show: Boolean = true,
    var formatter: String? = null,
    var rotate: Float? = null,
) : JSONable
