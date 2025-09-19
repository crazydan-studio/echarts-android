var myChart;
var myChartTheme;
var myChartElement = 'container';
var myChartWrapper = window.__ChartWrapper__;

function initChart(useDarkTheme) {
    var dom = document.getElementById(myChartElement);

    myChartTheme = useDarkTheme ? 'dark' : null;
    myChart = echarts.init(dom, myChartTheme);

    // https://echarts.apache.org/en/api.html#events.rendered
    myChart.on('rendered', function () {
        myChartWrapper.onChartRendered();
    });

    myChartWrapper.onChartReady();
}

/** 更新图表配置及数据 */
function updateChartOptions(optionJson) {
    //console.log(optionJson);
    var option = JSON.parse(optionJson, function (key, value) {
        if (key == 'formatter'
            && typeof value === 'string'
            && value.startsWith('function')
        ) {
            return new Function('return ' + value)();
        }
        return value;
    });

    // https://echarts.apache.org/en/option.html#title
    // https://echarts.apache.org/en/api.html#echartsInstance.setOption
    myChart.setOption(option);
}

/** 创建浮动提示 */
function createTooltip_v1(title, data) {
    if (data.length == 0) {
        return null;
    }

    var html = [];

    html.push('<div class="echarts-tooltip container ' + (myChartTheme || '') + '">');
    html.push('  <div class="container">');
    html.push('    <div class="label axis-value-label">');
    html.push('      ' + title);
    html.push('    </div>');
    html.push('    <div class="body">');

    data.forEach(function(item) {
        html.push(
            createTooltip_v1_renderItem(item, 'main-marker')
        );

        (item.data || []).forEach(function(subItem) {
            html.push(
                createTooltip_v1_renderItem(subItem, 'sub-marker')
            );
        });
    });

    html.push('    </div>');
    html.push('  </div>');
    html.push('</div>');

    return html.join('');
}

function createTooltip_v1_renderItem(item, markerClass) {
    var html = [];
    var value = item.value || (item.data ? '' : '-');

    html.push('      <div class="container">');
    html.push('        <div class="container">');
    html.push('          <span class="' + markerClass + '" style="background-color: ' + item.color + ';"></span>');
    html.push('          <span class="label series-name">' + item.name + '</span>');
    html.push('          <span class="label series-value">' + value + '</span>');
    html.push('        </div>');
    html.push('      </div>');

    return html.join('');
}