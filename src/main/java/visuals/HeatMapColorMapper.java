package visuals;

import java.awt.Color;

/**
 * Utility class for mapping height values to colors using a custom 256-color colormap.
 */
public class HeatMapColorMapper {

    /**
     * Array representing the 256-color map.
     */
    private static final Color[] COLOR_MAP = new Color[256];

    static {
        // Those are honestly stolen from matplotlib:terrain colormap
        COLOR_MAP[0] = new Color(51, 51, 153);
        COLOR_MAP[1] = new Color(49, 53, 155);
        COLOR_MAP[2] = new Color(48, 56, 158);
        COLOR_MAP[3] = new Color(47, 59, 161);
        COLOR_MAP[4] = new Color(45, 61, 163);
        COLOR_MAP[5] = new Color(44, 64, 166);
        COLOR_MAP[6] = new Color(43, 67, 169);
        COLOR_MAP[7] = new Color(41, 69, 171);
        COLOR_MAP[8] = new Color(40, 72, 174);
        COLOR_MAP[9] = new Color(39, 75, 177);
        COLOR_MAP[10] = new Color(37, 77, 179);
        COLOR_MAP[11] = new Color(36, 80, 182);
        COLOR_MAP[12] = new Color(35, 83, 185);
        COLOR_MAP[13] = new Color(33, 85, 187);
        COLOR_MAP[14] = new Color(32, 88, 190);
        COLOR_MAP[15] = new Color(31, 91, 193);
        COLOR_MAP[16] = new Color(29, 93, 195);
        COLOR_MAP[17] = new Color(28, 96, 198);
        COLOR_MAP[18] = new Color(27, 98, 201);
        COLOR_MAP[19] = new Color(25, 101, 203);
        COLOR_MAP[20] = new Color(24, 104, 206);
        COLOR_MAP[21] = new Color(23, 107, 209);
        COLOR_MAP[22] = new Color(21, 109, 211);
        COLOR_MAP[23] = new Color(20, 112, 214);
        COLOR_MAP[24] = new Color(19, 115, 217);
        COLOR_MAP[25] = new Color(17, 117, 219);
        COLOR_MAP[26] = new Color(16, 120, 222);
        COLOR_MAP[27] = new Color(14, 123, 225);
        COLOR_MAP[28] = new Color(13, 125, 227);
        COLOR_MAP[29] = new Color(12, 128, 230);
        COLOR_MAP[30] = new Color(11, 131, 233);
        COLOR_MAP[31] = new Color(9, 133, 235);
        COLOR_MAP[32] = new Color(8, 136, 238);
        COLOR_MAP[33] = new Color(7, 138, 241);
        COLOR_MAP[34] = new Color(5, 141, 243);
        COLOR_MAP[35] = new Color(4, 144, 246);
        COLOR_MAP[36] = new Color(3, 147, 249);
        COLOR_MAP[37] = new Color(1, 149, 251);
        COLOR_MAP[38] = new Color(0, 152, 254);
        COLOR_MAP[39] = new Color(0, 154, 250);
        COLOR_MAP[40] = new Color(0, 156, 244);
        COLOR_MAP[41] = new Color(0, 158, 238);
        COLOR_MAP[42] = new Color(0, 160, 232);
        COLOR_MAP[43] = new Color(0, 162, 226);
        COLOR_MAP[44] = new Color(0, 164, 220);
        COLOR_MAP[45] = new Color(0, 166, 214);
        COLOR_MAP[46] = new Color(0, 168, 208);
        COLOR_MAP[47] = new Color(0, 170, 202);
        COLOR_MAP[48] = new Color(0, 172, 196);
        COLOR_MAP[49] = new Color(0, 174, 190);
        COLOR_MAP[50] = new Color(0, 176, 184);
        COLOR_MAP[51] = new Color(0, 178, 178);
        COLOR_MAP[52] = new Color(0, 180, 172);
        COLOR_MAP[53] = new Color(0, 182, 166);
        COLOR_MAP[54] = new Color(0, 184, 160);
        COLOR_MAP[55] = new Color(0, 186, 154);
        COLOR_MAP[56] = new Color(0, 188, 148);
        COLOR_MAP[57] = new Color(0, 190, 142);
        COLOR_MAP[58] = new Color(0, 192, 136);
        COLOR_MAP[59] = new Color(0, 194, 130);
        COLOR_MAP[60] = new Color(0, 196, 124);
        COLOR_MAP[61] = new Color(0, 198, 118);
        COLOR_MAP[62] = new Color(0, 200, 112);
        COLOR_MAP[63] = new Color(0, 202, 106);
        COLOR_MAP[64] = new Color(1, 204, 102);
        COLOR_MAP[65] = new Color(5, 205, 103);
        COLOR_MAP[66] = new Color(8, 205, 103);
        COLOR_MAP[67] = new Color(13, 206, 104);
        COLOR_MAP[68] = new Color(17, 207, 105);
        COLOR_MAP[69] = new Color(21, 208, 106);
        COLOR_MAP[70] = new Color(25, 209, 107);
        COLOR_MAP[71] = new Color(29, 209, 107);
        COLOR_MAP[72] = new Color(33, 210, 108);
        COLOR_MAP[73] = new Color(37, 211, 109);
        COLOR_MAP[74] = new Color(40, 212, 110);
        COLOR_MAP[75] = new Color(45, 213, 111);
        COLOR_MAP[76] = new Color(49, 213, 111);
        COLOR_MAP[77] = new Color(53, 214, 112);
        COLOR_MAP[78] = new Color(57, 215, 113);
        COLOR_MAP[79] = new Color(61, 216, 114);
        COLOR_MAP[80] = new Color(65, 217, 115);
        COLOR_MAP[81] = new Color(69, 217, 115);
        COLOR_MAP[82] = new Color(72, 218, 116);
        COLOR_MAP[83] = new Color(77, 219, 117);
        COLOR_MAP[84] = new Color(81, 220, 118);
        COLOR_MAP[85] = new Color(85, 221, 119);
        COLOR_MAP[86] = new Color(89, 221, 119);
        COLOR_MAP[87] = new Color(93, 222, 120);
        COLOR_MAP[88] = new Color(97, 223, 121);
        COLOR_MAP[89] = new Color(101, 224, 122);
        COLOR_MAP[90] = new Color(104, 225, 122);
        COLOR_MAP[91] = new Color(109, 225, 123);
        COLOR_MAP[92] = new Color(113, 226, 124);
        COLOR_MAP[93] = new Color(117, 227, 125);
        COLOR_MAP[94] = new Color(121, 228, 126);
        COLOR_MAP[95] = new Color(125, 229, 127);
        COLOR_MAP[96] = new Color(129, 229, 127);
        COLOR_MAP[97] = new Color(133, 230, 128);
        COLOR_MAP[98] = new Color(136, 231, 129);
        COLOR_MAP[99] = new Color(141, 232, 130);
        COLOR_MAP[100] = new Color(145, 233, 131);
        COLOR_MAP[101] = new Color(149, 233, 131);
        COLOR_MAP[102] = new Color(153, 234, 132);
        COLOR_MAP[103] = new Color(157, 235, 133);
        COLOR_MAP[104] = new Color(161, 236, 134);
        COLOR_MAP[105] = new Color(165, 237, 135);
        COLOR_MAP[106] = new Color(168, 237, 135);
        COLOR_MAP[107] = new Color(173, 238, 136);
        COLOR_MAP[108] = new Color(177, 239, 137);
        COLOR_MAP[109] = new Color(181, 240, 138);
        COLOR_MAP[110] = new Color(185, 241, 139);
        COLOR_MAP[111] = new Color(189, 241, 139);
        COLOR_MAP[112] = new Color(193, 242, 140);
        COLOR_MAP[113] = new Color(197, 243, 141);
        COLOR_MAP[114] = new Color(200, 244, 142);
        COLOR_MAP[115] = new Color(205, 245, 143);
        COLOR_MAP[116] = new Color(209, 245, 143);
        COLOR_MAP[117] = new Color(213, 246, 144);
        COLOR_MAP[118] = new Color(217, 247, 145);
        COLOR_MAP[119] = new Color(221, 248, 146);
        COLOR_MAP[120] = new Color(225, 249, 147);
        COLOR_MAP[121] = new Color(229, 249, 147);
        COLOR_MAP[122] = new Color(232, 250, 148);
        COLOR_MAP[123] = new Color(237, 251, 149);
        COLOR_MAP[124] = new Color(241, 252, 150);
        COLOR_MAP[125] = new Color(245, 253, 151);
        COLOR_MAP[126] = new Color(249, 253, 151);
        COLOR_MAP[127] = new Color(253, 254, 152);
        COLOR_MAP[128] = new Color(254, 253, 152);
        COLOR_MAP[129] = new Color(252, 251, 151);
        COLOR_MAP[130] = new Color(250, 248, 150);
        COLOR_MAP[131] = new Color(248, 246, 149);
        COLOR_MAP[132] = new Color(246, 243, 148);
        COLOR_MAP[133] = new Color(244, 240, 147);
        COLOR_MAP[134] = new Color(242, 238, 145);
        COLOR_MAP[135] = new Color(240, 235, 144);
        COLOR_MAP[136] = new Color(238, 233, 143);
        COLOR_MAP[137] = new Color(236, 230, 142);
        COLOR_MAP[138] = new Color(234, 228, 141);
        COLOR_MAP[139] = new Color(232, 225, 140);
        COLOR_MAP[140] = new Color(230, 223, 139);
        COLOR_MAP[141] = new Color(228, 220, 138);
        COLOR_MAP[142] = new Color(226, 217, 137);
        COLOR_MAP[143] = new Color(224, 215, 136);
        COLOR_MAP[144] = new Color(222, 212, 135);
        COLOR_MAP[145] = new Color(220, 210, 134);
        COLOR_MAP[146] = new Color(218, 207, 133);
        COLOR_MAP[147] = new Color(216, 205, 131);
        COLOR_MAP[148] = new Color(214, 202, 130);
        COLOR_MAP[149] = new Color(211, 199, 129);
        COLOR_MAP[150] = new Color(210, 197, 128);
        COLOR_MAP[151] = new Color(208, 194, 127);
        COLOR_MAP[152] = new Color(206, 192, 126);
        COLOR_MAP[153] = new Color(204, 189, 125);
        COLOR_MAP[154] = new Color(202, 187, 124);
        COLOR_MAP[155] = new Color(200, 184, 123);
        COLOR_MAP[156] = new Color(198, 182, 122);
        COLOR_MAP[157] = new Color(195, 179, 121);
        COLOR_MAP[158] = new Color(194, 176, 120);
        COLOR_MAP[159] = new Color(192, 174, 118);
        COLOR_MAP[160] = new Color(190, 171, 117);
        COLOR_MAP[161] = new Color(188, 169, 116);
        COLOR_MAP[162] = new Color(186, 166, 115);
        COLOR_MAP[163] = new Color(184, 164, 114);
        COLOR_MAP[164] = new Color(182, 161, 113);
        COLOR_MAP[165] = new Color(179, 159, 112);
        COLOR_MAP[166] = new Color(178, 156, 111);
        COLOR_MAP[167] = new Color(176, 153, 110);
        COLOR_MAP[168] = new Color(174, 151, 109);
        COLOR_MAP[169] = new Color(172, 148, 108);
        COLOR_MAP[170] = new Color(170, 146, 107);
        COLOR_MAP[171] = new Color(168, 143, 106);
        COLOR_MAP[172] = new Color(166, 141, 104);
        COLOR_MAP[173] = new Color(163, 138, 103);
        COLOR_MAP[174] = new Color(162, 135, 102);
        COLOR_MAP[175] = new Color(160, 133, 101);
        COLOR_MAP[176] = new Color(158, 130, 100);
        COLOR_MAP[177] = new Color(156, 128, 99);
        COLOR_MAP[178] = new Color(154, 125, 98);
        COLOR_MAP[179] = new Color(152, 123, 97);
        COLOR_MAP[180] = new Color(150, 120, 96);
        COLOR_MAP[181] = new Color(147, 118, 95);
        COLOR_MAP[182] = new Color(146, 115, 94);
        COLOR_MAP[183] = new Color(144, 112, 93);
        COLOR_MAP[184] = new Color(142, 110, 91);
        COLOR_MAP[185] = new Color(140, 107, 90);
        COLOR_MAP[186] = new Color(138, 105, 89);
        COLOR_MAP[187] = new Color(136, 102, 88);
        COLOR_MAP[188] = new Color(134, 100, 87);
        COLOR_MAP[189] = new Color(131, 97, 86);
        COLOR_MAP[190] = new Color(130, 95, 85);
        COLOR_MAP[191] = new Color(128, 92, 84);
        COLOR_MAP[192] = new Color(129, 93, 86);
        COLOR_MAP[193] = new Color(131, 96, 88);
        COLOR_MAP[194] = new Color(133, 98, 91);
        COLOR_MAP[195] = new Color(135, 101, 94);
        COLOR_MAP[196] = new Color(136, 103, 96);
        COLOR_MAP[197] = new Color(139, 106, 99);
        COLOR_MAP[198] = new Color(141, 109, 102);
        COLOR_MAP[199] = new Color(143, 111, 104);
        COLOR_MAP[200] = new Color(145, 114, 107);
        COLOR_MAP[201] = new Color(147, 116, 110);
        COLOR_MAP[202] = new Color(149, 119, 112);
        COLOR_MAP[203] = new Color(151, 121, 115);
        COLOR_MAP[204] = new Color(153, 124, 118);
        COLOR_MAP[205] = new Color(155, 127, 121);
        COLOR_MAP[206] = new Color(157, 129, 123);
        COLOR_MAP[207] = new Color(159, 132, 126);
        COLOR_MAP[208] = new Color(161, 134, 129);
        COLOR_MAP[209] = new Color(163, 137, 131);
        COLOR_MAP[210] = new Color(165, 139, 134);
        COLOR_MAP[211] = new Color(167, 142, 137);
        COLOR_MAP[212] = new Color(168, 144, 139);
        COLOR_MAP[213] = new Color(171, 147, 142);
        COLOR_MAP[214] = new Color(173, 150, 145);
        COLOR_MAP[215] = new Color(175, 152, 147);
        COLOR_MAP[216] = new Color(177, 155, 150);
        COLOR_MAP[217] = new Color(179, 157, 153);
        COLOR_MAP[218] = new Color(181, 160, 155);
        COLOR_MAP[219] = new Color(183, 162, 158);
        COLOR_MAP[220] = new Color(185, 165, 161);
        COLOR_MAP[221] = new Color(187, 167, 163);
        COLOR_MAP[222] = new Color(189, 170, 166);
        COLOR_MAP[223] = new Color(191, 173, 169);
        COLOR_MAP[224] = new Color(193, 175, 171);
        COLOR_MAP[225] = new Color(195, 178, 174);
        COLOR_MAP[226] = new Color(196, 180, 177);
        COLOR_MAP[227] = new Color(199, 183, 179);
        COLOR_MAP[228] = new Color(200, 185, 182);
        COLOR_MAP[229] = new Color(203, 188, 185);
        COLOR_MAP[230] = new Color(205, 191, 187);
        COLOR_MAP[231] = new Color(207, 193, 190);
        COLOR_MAP[232] = new Color(209, 196, 193);
        COLOR_MAP[233] = new Color(211, 198, 196);
        COLOR_MAP[234] = new Color(212, 201, 198);
        COLOR_MAP[235] = new Color(215, 203, 201);
        COLOR_MAP[236] = new Color(217, 206, 204);
        COLOR_MAP[237] = new Color(219, 208, 206);
        COLOR_MAP[238] = new Color(221, 211, 209);
        COLOR_MAP[239] = new Color(223, 214, 212);
        COLOR_MAP[240] = new Color(225, 216, 214);
        COLOR_MAP[241] = new Color(227, 219, 217);
        COLOR_MAP[242] = new Color(228, 221, 220);
        COLOR_MAP[243] = new Color(231, 224, 222);
        COLOR_MAP[244] = new Color(232, 226, 225);
        COLOR_MAP[245] = new Color(235, 229, 228);
        COLOR_MAP[246] = new Color(237, 231, 230);
        COLOR_MAP[247] = new Color(239, 234, 233);
        COLOR_MAP[248] = new Color(241, 237, 236);
        COLOR_MAP[249] = new Color(243, 239, 238);
        COLOR_MAP[250] = new Color(244, 242, 241);
        COLOR_MAP[251] = new Color(247, 244, 244);
        COLOR_MAP[252] = new Color(249, 247, 246);
        COLOR_MAP[253] = new Color(251, 249, 249);
        COLOR_MAP[254] = new Color(253, 252, 252);
        COLOR_MAP[255] = new Color(255, 255, 255);
    }

    /**
     * Maps a height value to a Color object using the custom 256-color colormap.
     *
     * @param height     The height value of the node.
     * @param minHeight  The minimum height in the dataset.
     * @param maxHeight  The maximum height in the dataset.
     * @return The Color corresponding to the height.
     */
    public static Color getColorForHeight(int height, int minHeight, int maxHeight) {
        if (maxHeight == minHeight) {
            // Avoid division by zero; return a default color (e.g., middle of the color map)
            return COLOR_MAP[128];
        }

        // Normalize the height to a value between 0 and 255
        double ratio = (double) (height - minHeight) / (maxHeight - minHeight);
        ratio = Math.max(0.0, Math.min(1.0, ratio)); // Clamp between 0 and 1

        // Map the ratio to an index in the color map
        int index = (int) Math.round(ratio * 255);

        // Ensure the index is within bounds
        index = Math.max(0, Math.min(255, index));

        return COLOR_MAP[index];
    }
}
