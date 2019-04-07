[#macro paging pageNum pageSize pages]
    [#if (pageNum>1)]
    <li><a aria-label="Previous" href="&pagenum=${pageNum-1}"><span aria-hidden="true">«</span></a></li>
    [/#if]


    [#assign begin=pageNum-3]

    [#if (pages-pageNum) lt 3]
        [#assign begin=pages-6]
    [/#if]

    [#if pageNum lt 4]
        [#if pages gt 7]
            [#assign end=7]
        [#else]
            [#assign end=pages]
        [/#if]
    [#else]
        [#assign end=pageNum+3]
    [/#if]

    [#list begin..end as index]
        [#if pages gt 1]
            [#if (index >= 1)&&(index <= pages)]
            <li [#if index == pageNum]
                    class="active"[/#if]><a href="&pagenum=${index}">${index}</a></li>
            [/#if]
        [/#if]
    [/#list]
    [#if (pages>pageNum)]
    <li><a aria-label="Next" href="&pagenum=${pageNum+1}"><span aria-hidden="true">»</span></a></li>
    [/#if]
[/#macro]