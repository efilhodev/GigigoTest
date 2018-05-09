package br.com.efilho.gigigotest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import br.com.efilho.gigigotest.utils.DateUtil;

public class DateUtilTest {

    @Test
    public void testConvertStringToDate(){
        Assert.assertEquals(new Date(1525748400000L).getTime(), DateUtil.convertStringToDate("08/05/2018", DateUtil.APP_DATE_FORMAT).getTime());
        Assert.assertEquals(new Date(1525748400000L).getTime(), DateUtil.convertStringToDate("2018-05-08T00:00:00", DateUtil.API_DATE_FORMAT).getTime());
    }

    @Test
    public void testConvertDateToString(){
        Assert.assertEquals("08/05/2018", DateUtil.convertDateToString(new Date(1525748400000L), DateUtil.APP_DATE_FORMAT));
        Assert.assertEquals("2018-05-08T00:00:00", DateUtil.convertDateToString(new Date(1525748400000L), DateUtil.API_DATE_FORMAT));
    }
}
