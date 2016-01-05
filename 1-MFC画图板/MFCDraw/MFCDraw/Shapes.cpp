#include "stdafx.h"
#include "Shapes.h"
#include "MFCDraw.h"


IMPLEMENT_SERIAL(Shapes, CObject, 1)
Shapes::Shapes()
{

}


void Shapes::Serialize(CArchive& ar)
{
	CObject::Serialize(ar);
	if (ar.IsStoring())
	{
		ar << m_DrawingType << m_startPoint << m_endPoint;//�洢ͼ�ε�λ��,������
		if (m_DrawingType == 5)//����洢
		{
			ar << m_logFont.lfHeight << m_logFont.lfWidth << m_logFont.lfEscapement
				<< m_logFont.lfOrientation << m_logFont.lfWeight << m_logFont.lfItalic
				<< m_logFont.lfUnderline << m_logFont.lfStrikeOut << m_logFont.lfCharSet
				<< m_logFont.lfOutPrecision << m_logFont.lfClipPrecision << m_logFont.lfQuality
				<< m_logFont.lfPitchAndFamily;
			int i = 0;
			for (i = 0; i < LF_FACESIZE; i++)//LF_FACESIZE=32
			{
				ar << m_logFont.lfFaceName[i];//char����Ҫ��ÿ��Ԫ��һһȡ���ֱ�洢
			}
			ar << m_Text;
			ar << m_fontColor;
			return;
		}
		ar << m_penStyle << m_penWide << m_penColor;//ar<<m_Pen;ֻ�ܴ洢��������������
		if (m_DrawingType == 1 || m_DrawingType == 6)
		{
			ar << m_startPoint << m_endPoint << (DWORD)m_penColor;
			return;
		}
		else
		{
			ar << m_pictureFourStyle << m_brushStyle << m_brushColor;//�洢ͼ����ʽ����ˢ
		}
	}
	else
	{
		ar >> m_DrawingType >> m_startPoint >> m_endPoint;//��ȡͼ�ε�λ��,������
		if (m_DrawingType == 5)//����洢
		{
			ar >> m_logFont.lfHeight >> m_logFont.lfWidth >> m_logFont.lfEscapement
				>> m_logFont.lfOrientation >> m_logFont.lfWeight >> m_logFont.lfItalic
				>> m_logFont.lfUnderline >> m_logFont.lfStrikeOut >> m_logFont.lfCharSet
				>> m_logFont.lfOutPrecision >> m_logFont.lfClipPrecision >> m_logFont.lfQuality
				>> m_logFont.lfPitchAndFamily;
			int i = 0;
			for (i = 0; i < LF_FACESIZE; i++)//sizeof(m_logFont.lfFaceName
			{
				ar >> m_logFont.lfFaceName[i];//char����Ҫ��ÿ��Ԫ��һһȡ���ֱ�洢
			}
			ar >> m_Text;
			ar >> m_fontColor;
			return;
		}
		ar >> m_penStyle >> m_penWide >> m_penColor;
		if (m_DrawingType == 1 || m_DrawingType == 6)
		{
			ar >> m_startPoint >> m_endPoint >> (DWORD)m_penColor;
			return;
		}
		else
		{
			ar >> m_pictureFourStyle >> m_brushStyle >> m_brushColor;//��ȡͼ����ʽ����ˢ
		}
	}
}

Shapes::~Shapes()
{

}

