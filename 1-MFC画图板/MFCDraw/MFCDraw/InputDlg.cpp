// InputDlg.cpp : ʵ���ļ�
//

#include "stdafx.h"
#include "MFCDraw.h"
#include "InputDlg.h"
#include "afxdialogex.h"


// InputDlg �Ի���

IMPLEMENT_DYNAMIC(InputDlg, CDialogEx)

InputDlg::InputDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(IDD_DIALOG1, pParent)
	, m_Edit(_T(""))
{

}

InputDlg::~InputDlg()
{
}

void InputDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, m_Edit);
}


BEGIN_MESSAGE_MAP(InputDlg, CDialogEx)
END_MESSAGE_MAP()


// InputDlg ��Ϣ�������
