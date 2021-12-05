package com.wzj.spring01;

import com.wzj.spring01.entity.Klass;
import com.wzj.spring01.entity.Student;
import com.wzj.spring01.service.ISchool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * （必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
 * （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter
 * @author wangzhijie
 */
@SpringBootApplication(scanBasePackages = "com.wzj.spring01")
@MapperScan("com.wzj.spring01.mapper")
public class Spring01Application {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Student student1 = (Student) context.getBean("student1");
		System.out.println(student1);

		Klass class1 = context.getBean(Klass.class);
		System.out.println(class1);
		System.out.println("Klass对象AOP代理后的实际类型："+class1.getClass());
		System.out.println("Klass对象AOP代理后的实际类型是否是Klass子类："+(class1 instanceof Klass));

		ISchool school1 = context.getBean(ISchool.class);
		System.out.println(school1);
		System.out.println("ISchool接口的对象AOP代理后的实际类型："+school1.getClass());
		System.out.println("school1对象AOP代理后的实际类型是否是ISchool子类："+(school1 instanceof ISchool));

		school1.ding();

		class1.dong();

		SpringApplication.run(Spring01Application.class, args);

	}

}
