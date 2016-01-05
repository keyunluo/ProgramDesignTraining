#pragma once
#include "afx.h"
class Shapes :
	public CObject
{
	DECLARE_SERIAL(Shapes)
public:

	int       m_DrawingType;//标志画的是矩形、直线、椭圆等类型

	COLORREF  m_penColor;//颜色序列化
	int       m_penStyle;
	int       m_penWide;
	int       m_brushStyle;
	COLORREF  m_brushColor;		   //画刷颜色序列化

	int       m_pictureFourStyle;/*针对封闭图形的四种方式
								 即只有边框、
								 有边框的6种线条填充(又包含6种)、
								 边框和填充、
								 无边框
								 的标志*/

	CPoint    m_startPoint;
	CPoint    m_endPoint;
	CString   m_Text;//记录写入的字符

	LOGFONT   m_logFont;
	COLORREF  m_fontColor;

	Shapes();
	virtual void Serialize(CArchive& ar);
	virtual ~Shapes();


};

