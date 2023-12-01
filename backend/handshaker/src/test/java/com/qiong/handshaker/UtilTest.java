package com.qiong.handshaker;


import com.qiong.handshaker.TestBean.Hobby;
import com.qiong.handshaker.TestBean.Person;
import com.qiong.handshaker.TestBean.PersonReciver;
import com.qiong.handshaker.utils.utils.bean.QBeanUntil;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void beanUtilTest() {
        // System.out.println(int.class.getSimpleName());

        Person p = new Person(100L, "AAA", true, 1, "HOBBY",
                new Hobby(2L, "唱歌"));

        PersonReciver res = QBeanUntil.convert(p, PersonReciver.class);

        System.out.println(res);
    }
}
