package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.DataZoom.FilterMode

@EChartsOption
open class DataZoomList(
    private val holder: MutableList<DataZoom>,
) {

    /** [内置型数据区域缩放](https://echarts.apache.org/zh/option.html#dataZoom-inside) */
    fun inside(block: DataZoomInside.() -> Unit) {
        val zoom = DataZoomInside().apply(block)
        holder.add(zoom)
    }

    /** [滑动条型数据区域缩放](https://echarts.apache.org/zh/option.html#dataZoom-slider) */
    fun slider(block: DataZoomSlider.() -> Unit) {
        val zoom = DataZoomSlider().apply(block)
        holder.add(zoom)
    }
}

/**
 * 内置型数据区域缩放
 *
 * 所谓『内置』，即内置在坐标系中
 * - 平移：在坐标系中滑动拖拽进行数据区域平移
 * - 缩放：
 * - - PC端：鼠标在坐标系范围内滚轮滚动（MAC触控板类同）
 * - - 移动端：在移动端触屏上，支持两指滑动缩放
 *
 * [说明文档](https://echarts.apache.org/zh/option.html#dataZoom-inside)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class DataZoomInside(
    private val holder: DataZoomHolder = DataZoomHolder(
        type = Type.Inside, disabled = false,
    )
) : DataZoom(holder) {

    /** 是否禁用 */
    fun disabled(value: Boolean) {
        holder.disabled = value
    }
}

/**
 * 滑动条型数据区域缩放
 *
 * 提供数据缩略图显示、缩放、刷选、拖拽、点击快速定位等数据筛选的功能
 *
 * [说明文档](https://echarts.apache.org/en/option.html#dataZoom-slider)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class DataZoomSlider(
    private val holder: DataZoomHolder = DataZoomHolder(
        type = Type.Slider, show = true,
    )
) : DataZoom(holder) {

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 与容器周边的间隔 */
    fun margin(block: Margin.() -> Unit) {
        Margin(holder).block()
    }
}

@EChartsOption
open class DataZoom(
    private val holder: DataZoomHolder,
) : JSONable {

    enum class Type {
        Inside, Slider
    }

    enum class FilterMode {
        Filter, WeakFilter, Empty, None
    }

    override fun toJSON(): String = holder.toJSON()

    /** [数据过滤模式](https://echarts.apache.org/zh/option.html#dataZoom-inside.filterMode) */
    fun filterMode(block: DataZoomFilterModeScope.() -> FilterMode) {
        holder.filterMode = DataZoomFilterModeScope().block()
    }

    /** 数据窗口范围 */
    fun window(block: DataZoomWindow.() -> Unit) {
        DataZoomWindow(holder).block()
    }
}

data class DataZoomHolder(
    var type: DataZoom.Type,

    // common
    var filterMode: FilterMode? = FilterMode.Filter,

    var start: Float? = null,
    var end: Float? = null,
    var startValue: Int? = null,
    var endValue: Int? = null,

    var xAxisIndex: List<Int>? = null,
    var yAxisIndex: List<Int>? = null,

    // for inside
    var disabled: Boolean? = null,

    // for slider
    var show: Boolean? = null,
) : MarginHolder()

@EChartsOption
class DataZoomWindow(
    private val holder: DataZoomHolder,
) {

    sealed class Size {
        class Percent(val value: Float) : Size()
        class Index(val value: Int) : Size()
    }

    /** 数轴序号 */
    inline val Int.idx: Size.Index
        get() = Size.Index(this)

    /** 数轴区间的百分比 */
    inline val Float.pct: Size.Percent
        get() = Size.Percent(this)

    /** 数据窗口范围：起始与结束值为数轴序号 */
    fun range(start: Size.Index, end: Size.Index) {
        holder.startValue = start.value
        holder.endValue = end.value
    }

    /** 数据窗口范围：起始与结束值为数轴区间的百分比 */
    fun range(start: Size.Percent, end: Size.Percent) {
        holder.start = start.value
        holder.end = end.value
    }
}

class DataZoomFilterModeScope {
    /**
     * 当前数据窗口外的数据，将被过滤掉。其会影响其他轴的数据范围，
     * 每个数据项，只要有一个维度在数据窗口外，整个数据项就会被过滤掉
     */
    val filter = FilterMode.Filter

    /**
     * 当前数据窗口外的数据，将被过滤掉。其会影响其他轴的数据范围，
     * 每个数据项，只有当全部维度都在数据窗口同侧外部，整个数据项才会被过滤掉
     */
    val weakFilter = FilterMode.WeakFilter

    /** 当前数据窗口外的数据，将被设置为空。其不会影响其他轴的数据范围 */
    val empty = FilterMode.Empty

    /** 不过滤数据，只改变数轴范围 */
    val none = FilterMode.None
}