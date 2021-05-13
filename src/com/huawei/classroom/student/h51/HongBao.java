package com.huawei.classroom.student.h51;

/**
 * @author super
 */
public class HongBao {

	/**
	 * 
	 * @param total  红包总金额，以元为单位，精确到分，系统测试的时候保证总金额至少够每人分得1分钱
	 * @param personCount 分红包的总人数>0
	 * @return 每个人分得的钱数
	 * 规则遵循微信分红包规则 例如：
	 * 要求 每人分得的钱数总和=total
	 * 每个人分得钱数必须是正数，且不能少于1分
	 * 
	 */
	public double[] getHongbao(double total,int personCount) {
		if (total < personCount * 0.01) {
			return null;
		}
		double[] result = new double[personCount];
		int remainCount = personCount;
		double remainMoney = total;
		int i;
		for (i = 0; i < personCount; i++) {
			double oneMoney;
			oneMoney = getOneRedPackage(remainMoney, remainCount);
			remainCount--;
			remainMoney -= oneMoney;
			result[i] = oneMoney;
		}
		return result;
	}

	public double getOneRedPackage(double remainMoney, int remainCount) {
		double min = 0.01;
		double max1 = remainMoney / remainCount * 2;
		double max2 = remainMoney - (remainCount - 1) * 0.01;
		double max = Math.min(max1, max2);
		double money;
		if (remainCount == 1) {
			money = remainMoney;
		} else {
			money = Math.random() * max;
			money = Math.max(money, min);
		}
		money = (double) Math.round(money * 100) / 100;
		return money;
	}
}
