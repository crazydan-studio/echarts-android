package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.JSONable

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
interface Size {

    companion object : Size {
        fun pixel(value: Int): Size = Pixel(value)
        fun percent(value: Float): Size = Percent(value)
    }
}

private class Pixel(val value: Int) : Size, JSONable {
    override fun toJSON(): String {
        return value.toString()
    }
}

private class Percent(val value: Float) : Size, JSONable {
    override fun toJSON(): String {
        return "\"$value%\""
    }
}