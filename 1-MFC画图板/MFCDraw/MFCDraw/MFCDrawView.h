
// MFCDrawView.h : CMFCDrawView ��Ľӿ�
//

#pragma once


class CMFCDrawView : public CView
{
protected: // �������л�����
	CMFCDrawView();
	DECLARE_DYNCREATE(CMFCDrawView)

// ����
public:
	CMFCDrawDoc* GetDocument() const;

// ����
public:

// ��д
public:
	virtual void OnDraw(CDC* pDC);  // ��д�Ի��Ƹ���ͼ
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// ʵ��
public:
	virtual ~CMFCDrawView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// ���ɵ���Ϣӳ�亯��
protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnDrawLine();
	afx_msg void OnDrawPencil();
	afx_msg void OnDrawRect();
	afx_msg void OnDrawEllipse();
	afx_msg void OnDrawFont();
	afx_msg void OnDrawRundrect();


// ����
public:

	int       DrawingType;

	COLORREF  penColor;//��ɫ������Ϊ�����ͣ����Կ���ֱ�����л�
	int       penStyle;
	int       penWide;
	int       brushStyle;
	COLORREF  brushColor;

	int       pictureFourStyle;
	CPoint    startPoint;
	CPoint    newEndPoint;
	CPoint    oldEndPoint;

	LOGFONT   logFont;
	CFont*    pFont;
	COLORREF  fontColor;
	CString   stringFont;

	BOOL      clickAndMove;//��־������������ťʱ�Ƿ񻭹�ͼ

	afx_msg void OnFont();
	afx_msg void OnDrawColor();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnChangeLineWide(UINT uID);
	afx_msg void OnLinePenStyle(UINT uID);
	afx_msg void OnPictureStyle(UINT uID);
	afx_msg void OnLineFillStyle(UINT uID);

};

#ifndef _DEBUG  // MFCDrawView.cpp �еĵ��԰汾
inline CMFCDrawDoc* CMFCDrawView::GetDocument() const
   { return reinterpret_cast<CMFCDrawDoc*>(m_pDocument); }
#endif

