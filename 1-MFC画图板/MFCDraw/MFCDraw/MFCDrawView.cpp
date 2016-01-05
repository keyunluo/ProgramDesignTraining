
// MFCDrawView.cpp : CMFCDrawView ���ʵ��
//

#include "stdafx.h"
// SHARED_HANDLERS ������ʵ��Ԥ��������ͼ������ɸѡ�������
// ATL ��Ŀ�н��ж��壬�����������Ŀ�����ĵ����롣
#ifndef SHARED_HANDLERS
#include "MFCDraw.h"
#endif

#include "MFCDrawDoc.h"
#include "MFCDrawView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

#include "InputDlg.h"
// CMFCDrawView

IMPLEMENT_DYNCREATE(CMFCDrawView, CView)

BEGIN_MESSAGE_MAP(CMFCDrawView, CView)
	// ��׼��ӡ����
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CView::OnFilePrintPreview)
	ON_COMMAND(ID_DRAW_LINE, &CMFCDrawView::OnDrawLine)
	ON_COMMAND(ID_DRAW_PENCIL, &CMFCDrawView::OnDrawPencil)
	ON_COMMAND(ID_DRAW_RECT, &CMFCDrawView::OnDrawRect)
	ON_COMMAND(ID_DRAW_ELLIPSE, &CMFCDrawView::OnDrawEllipse)
	ON_COMMAND(ID_DRAW_FONT, &CMFCDrawView::OnDrawFont)
	ON_COMMAND(ID_DRAW_RUNDRECT, &CMFCDrawView::OnDrawRundrect)
	ON_COMMAND(ID_LOGFONT, &CMFCDrawView::OnFont)
	ON_COMMAND(ID_DRAW_COLOR, &CMFCDrawView::OnDrawColor)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()

	//�������
	ON_COMMAND_RANGE(ID_LINE_MIN, ID_LINE_ADDWIDE, OnChangeLineWide)

	//������ʽӳ��
	ON_COMMAND_RANGE(ID_PS_SOLID, ID_PS_INSIDEFRAME, OnLinePenStyle)
	//ͼ����ʽӳ��
	ON_COMMAND_RANGE(ID_ONLY_FRAME, ID_ONLY_FILL, OnPictureStyle)
	//�������������ʽӳ��
	ON_COMMAND_RANGE(ID_HS_CROSS, ID_HS_VERTICAL, OnLineFillStyle)

END_MESSAGE_MAP()

// CMFCDrawView ����/����

CMFCDrawView::CMFCDrawView()
{
	clickAndMove = FALSE;
	DrawingType = 1;//��ʼΪֱ��
	penStyle = PS_SOLID;//ʵ��
	penWide = 1;//���Ϊһ������
	penColor = RGB(128, 128, 128);//Ĭ��Ϊ��ɫ  
	brushStyle = HS_CROSS;//��ʼΪ����
	brushColor = RGB(255, 255, 255);//��ʼ��Ϊ��ɫ
	pictureFourStyle = 1;//��ʼΪֻ�б߿�,�����
	pFont = NULL;
}

CMFCDrawView::~CMFCDrawView()
{
}

BOOL CMFCDrawView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: �ڴ˴�ͨ���޸�
	//  CREATESTRUCT cs ���޸Ĵ��������ʽ

	return CView::PreCreateWindow(cs);
}

// CMFCDrawView ����

void CMFCDrawView::OnDraw(CDC*  pDC)
{
	CMFCDrawDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	POSITION pos = pDoc->shapeArray.GetHeadPosition();
	while (pos != NULL)
	{
		Shapes *shp = pDoc->shapeArray.GetNext(pos);
		if (shp->m_DrawingType == 1)
		{
			CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(shp->m_startPoint);
			pDC->LineTo(shp->m_endPoint);
		}
		else if (shp->m_DrawingType == 2)
		{
			if (shp->m_pictureFourStyle == 1)//�б߿������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 2)//�б߿򣬰�ɫ���
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 3)//�ޱ߿���ɫ���
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 4)//�б߿��������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Rectangle(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}

		}
		else if (shp->m_DrawingType == 3)
		{
			if (shp->m_pictureFourStyle == 1)//�б߿������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 2)//�б߿򣬰����
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 3)//�ޱ߿���ɫ���
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
			else if (shp->m_pictureFourStyle == 4)//�б߿��������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->RoundRect(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y, 18, 18);
			}
		}
		else if (shp->m_DrawingType == 4)//����Բ
		{
			if (shp->m_pictureFourStyle == 1)//�б߿������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				pDC->SelectObject(&pen);
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 2)//�б߿򣬰����
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 3)//�ޱ߿���ɫ���
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
			else if (shp->m_pictureFourStyle == 4)//�б߿��������
			{
				CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
				CBrush brush(shp->m_brushStyle, shp->m_brushColor);
				pDC->SelectObject(&pen);
				pDC->SelectObject(&brush);
				pDC->Ellipse(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_endPoint.x, shp->m_endPoint.y);
			}
		}
		else if (shp->m_DrawingType == 5)//���ַ�
		{
			pFont = new CFont();
			pFont->CreateFontIndirect(&shp->m_logFont);
			pDC->SetBkMode(TRANSPARENT);
			pDC->SetTextColor(shp->m_fontColor);
			pDC->SelectObject(pFont);
			pDC->TextOut(shp->m_startPoint.x, shp->m_startPoint.y, shp->m_Text);
		}

		else if (shp->m_DrawingType == 6)//��Ǧ����
		{
			CPen pen(shp->m_penStyle, shp->m_penWide, shp->m_penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(shp->m_startPoint);
			pDC->LineTo(shp->m_endPoint);
		}
	}
}


// CMFCDrawView ��ӡ

BOOL CMFCDrawView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// Ĭ��׼��
	return DoPreparePrinting(pInfo);
}

void CMFCDrawView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: ��Ӷ���Ĵ�ӡǰ���еĳ�ʼ������
}

void CMFCDrawView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: ��Ӵ�ӡ����е��������
}


// CMFCDrawView ���

#ifdef _DEBUG
void CMFCDrawView::AssertValid() const
{
	CView::AssertValid();
}

void CMFCDrawView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CMFCDrawDoc* CMFCDrawView::GetDocument() const // �ǵ��԰汾��������
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CMFCDrawDoc)));
	return (CMFCDrawDoc*)m_pDocument;
}
#endif //_DEBUG


// CMFCDrawView ��Ϣ�������


void CMFCDrawView::OnDrawLine()
{
	DrawingType = 1;
}


void CMFCDrawView::OnDrawRect()
{
	DrawingType = 2;
}

void CMFCDrawView::OnDrawRundrect()
{
	DrawingType = 3;
}

void CMFCDrawView::OnDrawEllipse()
{
	DrawingType = 4;
}


void CMFCDrawView::OnDrawFont()
{
	if (!pFont)
	{
		MessageBox(L"�����������壡");
		return;
	}
	DrawingType = 5;
}

void CMFCDrawView::OnDrawPencil()
{
	DrawingType = 6;
}


void CMFCDrawView::OnFont()
{
	CFontDialog fontDialog(&logFont);
	int result = fontDialog.DoModal();

	if (result == IDOK)
	{
		pFont = new CFont();
		pFont->CreateFontIndirect(&logFont);
		fontColor = fontDialog.GetColor();
		MessageBox(L"�������óɹ�");
		return;
	}
	MessageBox(L"ȡ����������");
}


void CMFCDrawView::OnDrawColor()
{
	CColorDialog colorSet(0, 0, NULL);
	int result = colorSet.DoModal();
	if (result == IDOK)
	{
		brushColor = penColor = colorSet.GetColor();
	}
}

void CMFCDrawView::OnChangeLineWide(UINT uID)
{
	switch (uID)
	{
	case ID_LINE_MIN://ϸ
		penWide = 1;
		break;
	case ID_LINE_MID://��
		penWide = 5;
		break;
	case ID_LINE_WIDE://��
		penWide = 18;
		break;
	case ID_LINE_ADDWIDE://�ӿ�
		penWide++;
		if (penWide > 30)//�������Ϊ30������
		{
			penWide = 1;
		}
		break;
	}
}


//������ʽ����
void CMFCDrawView::OnLinePenStyle(UINT uID)
{
	switch (uID)
	{
	case ID_PS_SOLID:
		penStyle = PS_SOLID;
		break;
	case ID_PS_DASH:
		penStyle = PS_DASH;
		MessageBox(L"ѡ����PS_DASH");
		break;
	case ID_PS_DOT:
		penStyle = PS_DOT;
		break;
	case ID_PS_DASHDOT:
		penStyle = PS_DASHDOT;
		MessageBox(L"ѡ����PS_DASHDOT");
		break;
	case ID_PS_DASHDOTDOT:
		penStyle = PS_DASHDOTDOT;
		break;
	case ID_PS_NULL:
		penStyle = PS_NULL;
		break;
	case ID_PS_INSIDEFRAME:
		penStyle = PS_INSIDEFRAME;
		break;
	}
}
//ͼ����ʽ����
void CMFCDrawView::OnPictureStyle(UINT uID)
{
	switch (uID)
	{
	case ID_ONLY_FRAME:
		pictureFourStyle = 1;//ֻ�б߿�
		break;
	case ID_FRAME_FILL:
		pictureFourStyle = 2;//�߿�����
		break;
	case ID_ONLY_FILL:
		pictureFourStyle = 3;//ֻ���
		penStyle = PS_SOLID;
		brushColor = penColor;
		break;
	}
}
//���������ʽ����
void CMFCDrawView::OnLineFillStyle(UINT uID)
{
	pictureFourStyle = 4;
	switch (uID)
	{
	case ID_HS_BDIAGONAL:
		brushStyle = HS_BDIAGONAL;
		break;
	case ID_HS_CROSS:
		brushStyle = HS_CROSS;
		break;
	case ID_HS_DIAGCROSS:
		brushStyle = HS_DIAGCROSS;
		break;
	case ID_HS_FDIAGONAL:
		brushStyle = HS_FDIAGONAL;
		break;
	case ID_HS_HORIZONTAL:
		brushStyle = HS_HORIZONTAL;
		break;
	case ID_HS_VERTICAL:
		brushStyle = HS_VERTICAL;
		break;
	}
}

void CMFCDrawView::OnLButtonDown(UINT nFlags, CPoint point)
{
	CMFCDrawDoc* pDoc = GetDocument();
	SetCapture();
	startPoint = oldEndPoint = newEndPoint = point;
	if (DrawingType == 5)//д��
	{
		InputDlg dlg;
		int result = dlg.DoModal();
		if (result == IDOK)
		{
			Shapes *sh = new Shapes();
			CString s = dlg.m_Edit;
			CDC*pDC = GetDC();
			//�������ֱ���͸��
			pDC->SetBkMode(TRANSPARENT);	 
			pDC->SelectObject(pFont);
			pDC->SetTextColor(fontColor);
			pDC->TextOut(point.x, point.y, s);
			if (!s.IsEmpty())
			{
				sh->m_DrawingType = DrawingType;
				sh->m_startPoint = point;
				sh->m_logFont = logFont;
				sh->m_fontColor = fontColor;
				sh->m_Text = s;
				pDoc->shapeArray.AddTail(sh);
				pDoc->SetModifiedFlag();
			}
		}
	}
	CView::OnLButtonDown(nFlags, point);
}


void CMFCDrawView::OnLButtonUp(UINT nFlags, CPoint point)
{
	ReleaseCapture();
	CDC *pDC = GetDC();
	CMFCDrawDoc *pDoc = GetDocument();
	if (clickAndMove)
	{
		Shapes *shap = new Shapes();
		CPen pen(penStyle, penWide, penColor);
		pDC->SelectObject(&pen);

		if (DrawingType == 1)//ֱ��
		{
			pDC->MoveTo(startPoint);
			pDC->LineTo(newEndPoint);
		}
		else if (DrawingType == 2)//����
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//�б߿򣬰����
			{
				brushColor = RGB(255, 255, 255);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{

				pDC->SelectObject(&pen);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 3)//Բ�Ǿ���
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 2)//�б߿򣬰����
			{
				brushColor = RGB(255, 255, 255);
				shap->m_brushColor = RGB(255, 255, 255);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{
				brushColor = penColor;
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
		}
		else if (DrawingType == 4)//��Բ
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				pDC->SelectStockObject(NULL_BRUSH);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//�б߿򣬰�ɫ���
			{
				brushColor = RGB(255, 255, 255);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{
				brushColor = penColor;
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);

				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 6)
		{
			CView::OnLButtonUp(nFlags, point);
			return;
		}

		shap->m_DrawingType = DrawingType;
		shap->m_startPoint = startPoint;
		shap->m_endPoint = newEndPoint;
		shap->m_penStyle = penStyle;
		shap->m_penWide = penWide;
		shap->m_penColor = penColor;
		//�������ֱ�ߣ�ֻҪ����������Ϣ�Ѿ��㹻
		shap->m_pictureFourStyle = pictureFourStyle;
		shap->m_brushStyle = brushStyle;
		shap->m_brushColor = brushColor;
		clickAndMove = FALSE;
		pDoc->shapeArray.AddTail(shap);
		pDoc->SetModifiedFlag();
	}
	CView::OnLButtonUp(nFlags, point);
}


void CMFCDrawView::OnMouseMove(UINT nFlags, CPoint point)
{
	newEndPoint = point;
	CDC*pDC = GetDC();
	if (nFlags&MK_LBUTTON)
	{
		clickAndMove = TRUE;
		if (DrawingType == 1)//ֱ��
		{
			CPen pen(penStyle, penWide, penColor);
			pDC->SelectObject(&pen);

			pDC->SetROP2(R2_XORPEN);

			pDC->MoveTo(startPoint);
			pDC->LineTo(oldEndPoint);

			pDC->MoveTo(startPoint);
			pDC->LineTo(newEndPoint);
		}
		else if (DrawingType == 2)//ֱ�Ǿ���
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				// MessageBox("ֱ�Ǿ���1");
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//�б߿򣬰�ɫ���
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Rectangle(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Rectangle(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 3)//Բ�Ǿ���
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
				//MessageBox("Բ�Ǿ���1");
			}
			else if (pictureFourStyle == 2)//�б߿򣬰����
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->RoundRect(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y, 18, 18);
				pDC->RoundRect(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y, 18, 18);
			}
		}
		else if (DrawingType == 4)//��Բ
		{
			if (pictureFourStyle == 1)//�б߿������
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->SelectStockObject(NULL_BRUSH);

				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 2)//�б߿򣬰����
			{
				CPen pen(penStyle, penWide, penColor);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 3)//�б߿���ɫ���
			{
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
			else if (pictureFourStyle == 4)//�б߿��������
			{
				brushColor = penColor;
				CPen pen(penStyle, penWide, penColor);
				CBrush brush(brushStyle, brushColor);
				pDC->SelectObject(&brush);
				pDC->SelectObject(&pen);
				pDC->SetROP2(R2_XORPEN);
				pDC->Ellipse(startPoint.x, startPoint.y, oldEndPoint.x, oldEndPoint.y);
				pDC->Ellipse(startPoint.x, startPoint.y, newEndPoint.x, newEndPoint.y);
			}
		}
		else if (DrawingType == 6)//ʵ��Ǧ�ʹ���
		{
			CPen pen(penStyle, penWide, penColor);
			pDC->SelectObject(&pen);
			pDC->MoveTo(oldEndPoint);
			pDC->LineTo(newEndPoint);

			CMFCDrawDoc *pDoc = GetDocument();
			Shapes *shap = new Shapes();
			shap->m_DrawingType = DrawingType;
		    shap->m_startPoint = oldEndPoint;
			shap->m_endPoint = newEndPoint;		
			shap->m_penStyle = penStyle;
			shap->m_penWide = penWide;
			shap->m_penColor = penColor;
			pDoc->shapeArray.AddTail(shap);
			pDoc->SetModifiedFlag();
		}
		oldEndPoint = newEndPoint;
	}

	CView::OnMouseMove(nFlags, point);
}
