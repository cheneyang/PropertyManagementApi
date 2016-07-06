package cn.edu.cqupt.wyglzx.controller.inner.v1;

import cn.edu.cqupt.wyglzx.common.DataResponse;
import cn.edu.cqupt.wyglzx.common.OutputEntityJsonView;
import cn.edu.cqupt.wyglzx.service.RecordService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cc on 16/6/25.
 */
@RestController
@RequestMapping("/api/private/v1/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @RequestMapping("/input")
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse inputRecord(@RequestParam("meter_id") Long meterId,
                                    @RequestParam("time") Long time,
                                    @RequestParam("value") Double value,
                                    @RequestParam(value = "reader", defaultValue = "") String reader,
                                    @RequestParam(value = "remark", required = false, defaultValue = "") String remark) {

        recordService.input(meterId, time, value, reader, remark);
        return new DataResponse();
    }

    @RequestMapping("/update")
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse updateRecord(@RequestParam("id") Long id,
                                     @RequestParam(value = "end", required = false, defaultValue = "0") Double end,
                                     @RequestParam(value = "reader", required = false, defaultValue = "") String reader,
                                     @RequestParam(value = "remark", required = false, defaultValue = "") String remark) {

        recordService.updateRecord(id, end, reader, remark);
        return new DataResponse();
    }


    @RequestMapping("/temp-list-by-node")
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse getListByNode(@RequestParam("node_id") Long nodeId,
                                      @RequestParam("year") Integer year,
                                      @RequestParam("month") Integer month) {

        return new DataResponse().put("record_list", recordService.getTempListByNodeAndTime(nodeId, year, month));
    }

    @RequestMapping("/last")
    @JsonView(OutputEntityJsonView.Basic.class)
    public DataResponse getLastRecord(@RequestParam("meter_id") Long meterId) {

        DataResponse dataResponse = new DataResponse();
        dataResponse.put("last_year_month", recordService.getLastYearMonthRecord(meterId));
        dataResponse.put("last_month", recordService.getLastMonthRecord(meterId));

        return dataResponse;
    }

}
