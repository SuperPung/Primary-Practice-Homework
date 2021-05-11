/**
 * 
 */
package com.huawei.classroom.student.h53;


/**
 * @author super
 *
 */
public class RabbitCount {

	/**
	 * 1对兔子出生以后经过180天可以生出一窝（2对）兔子，以后每隔90天繁殖一次生出一窝（2对）兔子
	 * 每对兔子的寿命是700天 
	 * @param startCount 第0天 开始的时候初生的兔子对数
	 * @param days 经过的天份数
	 * @return 目前系统中存活的兔子的对数
	 */
	public int getLivingRabbit(int startCount,int days) {
		RabbitNest rabbitNest = new RabbitNest(startCount);
		for (int i = 0; i < days; i++) {
			rabbitNest.oneDayPass();
		}
		return rabbitNest.getLivingRabbits();
	}
}
