var myChart;
var myChartElement = 'container';
var myChartWrapper = window.__ChartWrapper__;

function initChart(useDarkTheme) {
    var dom = document.getElementById(myChartElement);

    myChart = echarts.init(dom, useDarkTheme ? 'dark' : null);

    // https://echarts.apache.org/en/api.html#events.rendered
    myChart.on('rendered', function () {
        myChartWrapper.onChartRendered();
    });

    myChartWrapper.onChartReady();
}

/** 更新图表配置及数据 */
function updateChartOptions(optionJson) {
    console.log(optionJson);
    var option = JSON.parse(optionJson);

    // https://echarts.apache.org/en/option.html#title
    // https://echarts.apache.org/en/api.html#echartsInstance.setOption
    myChart.setOption(option);
}

/** 创建浮动提示 */
function createTooltip_v1(title, data) {
    var html = [];

    html.push('<div class="echarts-tooltip container">');
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

    html.push('      <div class="container">');
    html.push('        <div class="container">');
    html.push('          <span class="' + markerClass + '" style="background-color: ' + item.color + ';"></span>');
    html.push('          <span class="label series-name">' + item.name + '</span>');
    html.push('          <span class="label series-value">' + (item.value || '-') + '</span>');
    html.push('        </div>');
    html.push('      </div>');

    return html.join('');
}