#pragma once
#include "afx.h"
class Shapes :
	public CObject
{
	DECLARE_SERIAL(Shapes)
public:

	int       m_DrawingType;//��־�����Ǿ��Ρ�ֱ�ߡ���Բ������

	COLORREF  m_penColor;//��ɫ���л�
	int       m_penStyle;
	int       m_penWide;
	int       m_brushStyle;
	COLORREF  m_brushColor;		   //��ˢ��ɫ���л�

	int       m_pictureFourStyle;/*��Է��ͼ�ε����ַ�ʽ
								 ��ֻ�б߿�
								 �б߿��6���������(�ְ���6��)��
								 �߿����䡢
								 �ޱ߿�
								 �ı�־*/

	CPoint    m_startPoint;
	CPoint    m_endPoint;
	CString   m_Text;//��¼д����ַ�

	LOGFONT   m_logFont;
	COLORREF  m_fontColor;

	Shapes();
	virtual void Serialize(CArchive& ar);
	virtual ~Shapes();


};

