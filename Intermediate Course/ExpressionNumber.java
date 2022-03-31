package com.javatest;

public class ExpressionNumber {
    /**
     * 给定一个只由 0(假)、1(真)、&(逻辑与)、|(逻辑或)和^(异或)五种字符组成
     * 的字符串express，再给定一个布尔值 desired。返回express能有多少种组合
     * 方式，可以达到desired的结果。
     * 【举例】
     * express="1^0|0|1"，desired=false
     * 只有 1^((0|0)|1)和 1^(0|(0|1))的组合可以得到 false，返回 2。
     * express="1"，desired=false
     * 无组合则可以得到false，返回0。
     */

    public static boolean isValid(char[] exp) {
        if ((exp.length & 1) == 0) {
            return false;
        }
        for (int i = 0; i < exp.length; i = i + 2) {
            if ((exp[i] != '1') && (exp[i] != '0')) {
                return false;
            }
        }
        for (int i = 1; i < exp.length; i = i + 2) {
            if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')) {
                return false;
            }
        }
        return true;
    }

    public static int ways1(String express, boolean desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }
        return process(exp, desired, 0, exp.length - 1);
    }

    public static int process(char[] exp, boolean desired, int L, int R) {
        if (L == R) {
            if (exp[L] == '1') {
                return desired ? 1 : 0;
            } else {
                return desired ? 0 : 1;
            }
        }
        int res = 0;
        if (desired) {
            for (int i = L + 1; i < R; i += 2) {
                switch (exp[i]) {
                    case '&':
                        res += process(exp, true, L, i - 1) * process(exp, true, i + 1, R);
                        break;
                    case '|':
                        res += process(exp, true, L, i - 1) * process(exp, false, i + 1, R);
                        res += process(exp, false, L, i - 1) * process(exp, true, i + 1, R);
                        res += process(exp, true, L, i - 1) * process(exp, true, i + 1, R);
                        break;
                    case '^':
                        res += process(exp, true, L, i - 1) * process(exp, false, i + 1, R);
                        res += process(exp, false, L, i - 1) * process(exp, true, i + 1, R);
                        break;
                }
            }
        } else {
            for (int i = L + 1; i < R; i += 2) {
                switch (exp[i]) {
                    case '&':
                        res += process(exp, false, L, i - 1) * process(exp, true, i + 1, R);
                        res += process(exp, true, L, i - 1) * process(exp, false, i + 1, R);
                        res += process(exp, false, L, i - 1) * process(exp, false, i + 1, R);
                        break;
                    case '|':
                        res += process(exp, false, L, i - 1) * process(exp, false, i + 1, R);
                        break;
                    case '^':
                        res += process(exp, true, L, i - 1) * process(exp, true, i + 1, R);
                        res += process(exp, false, L, i - 1) * process(exp, false, i + 1, R);
                        break;
                }
            }
        }
        return res;
    }

    public static int ways2(String express, boolean desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }
        int[][] t = new int[exp.length][exp.length];
        int[][] f = new int[exp.length][exp.length];
        t[0][0] = exp[0] == '0' ? 0 : 1;
        f[0][0] = exp[0] == '1' ? 0 : 1;
        for (int i = 2; i < exp.length; i += 2) {
            t[i][i] = exp[i] == '0' ? 0 : 1;
            f[i][i] = exp[i] == '1' ? 0 : 1;
            for (int j = i - 2; j >= 0; j -= 2) {
                for (int k = j; k < i; k += 2) {
                    if (exp[k + 1] == '&') {
                        t[j][i] += t[j][k] * t[k + 2][i];
                        f[j][i] += (f[j][k] + t[j][k]) * f[k + 2][i] + f[j][k] * t[k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        t[j][i] += (f[j][k] + t[j][k]) * t[k + 2][i] + t[j][k] * f[k + 2][i];
                        f[j][i] += f[j][k] * f[k + 2][i];
                    } else {
                        t[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i];
                        f[j][i] += f[j][k] * f[k + 2][i] + t[j][k] * t[k + 2][i];
                    }
                }
            }
        }
        return desired ? t[0][t.length - 1] : f[0][f.length - 1];
    }

    public static void main(String[] args) {
        String express = "1^0&0|1&1^0&0^1|0|1&1";
        boolean desired = true;
        System.out.println(ways1(express, desired));
        System.out.println(ways2(express, desired));
    }
}
