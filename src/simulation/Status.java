package simulation;
//Enumeration for work order status
enum Status {COMP, WMATL, CUSREC, RDIRWO, REVIEW, DISPTCHD, OPSCOMP, WOTHERS, WLABR, PLANINPR, RTS, WASSIGN, SCHD, WPLAN, MTRECL,
	PLANREVISE, OPSRECL_HK, SCOPE, SCOPING, RECOFDEED, WSIGNOFF, WAPPR, WSCH, INPRG, APPR, CAN, CLOSE, HISTEDIT, CUSCONT, CUSCOMP, WREPR,
	WPRO, DISAPPR,NOACTVTY, MATLENTRD, SCOPNG, NOWORK, TECHCOMP, OPSRECL, WTCOMP, WTRCL, MTCOMP, FMCOMP, EITCOMP, RLABR, WPCOND,PLANCOMP,
	MATLREQD,MATLA,PAID,PENDING,TESTING,RETEST,REVISE,RELEASED,NOADDCOM,TESTCOMP,MITIGATE,HOLD,WMATLALC,INCOMP,DISAPR,REJECTED,
	WMATLRVW,WMATLPICK, MATLSENT,WIVGT,FLDCOMP,MATLREADY,PLANINPRG,NOACCESS,WCONTR,ASSIGNED,WOTHRS,NEEDRVW,WMOC
}

/*
Value	Description
COMP	Completed (COMP)
WMATL	Waiting on Material (WMATL)
CUSRECL	Customer Recall (CUSRECL)
RDIRWO	Redirected Work Order (RDIRWO)
REVIEW	Under Review (REVIEW)
DISPTCHD	Dispatched (DISPTCHD)
OPSCOMP	Operations Complete/Acceptance (OPSCOMP)
WOTHERS	Waiting on Others (WOTHERS)
WLABR	Awaiting Labor Resources (WLABR)
PLANINPR	DCS Planning In Progress (PLANINPR)
RTS	Ready to Schedule (RTS)
WASSIGN	Waiting To Be Assigned (WASSIGN)
SCHD	Scheduled (SCHD)
WPLAN	Waiting on Plan (WPLAN)
MTRECL	Maintenance Recall/Rejection (MTRECL)
PLANREVISE	Plan Revision (PLANREVISE)
OPSRECL-HK	Operations Recall/Rejection - Housekeeping (OPSRECL-HK)
SCOPE	To Be Scoped (SCOPE)
SCOPING	Scoping (SCOPING)
RECOFDEED	Recording of Deed (RECOFDEED)
WSIGNOFF	Awaiting Sign-off (WSIGNOFF)
WAPPR	Waiting on Approval (WAPPR)
WSCH	Waiting to Be Scheduled (WSCH)
INPRG	In Progress (INPRG)
APPR	Approved (APPR)
CAN	Canceled (CAN)
CLOSE	Closed (CLOSE)
HISTEDIT	Edited in History (HISTEDIT)
CUSCONT	Customer Contacted (CUSCONT)
CUSCOMP	Customer Complete (CUSCOMP)
WREPR	Waiting on Repair or Inspect'n (WREPR)
WPROJ	Awaiting Project (WPROJ)
DISAPPR	Disapproved (DISAPPR)
NOACTVTY	No Activity (NOACTVTY)
MATLENTRD	Material Entered (MATLENTRD)
SCOPNG	Scoping (SCOPNG)
NOWORK	No Work Performed (NOWORK)
TECHCOMP	Technician (Trade) Complete (TECHCOMP)
OPSRECL	Operations Recall/Rejection (OPSRECL)
WTCOMP	Wastewater Treatment Complete (WTCOMP)
WTRCL	Wastewater Treatment Recall (WTRCL)
MTCOMP	Maintenance Complete (MTCOMP)
FMCOMP	Facilities Management Complete (FMCOMP)
EITCOMP	Engineering Complete (EITCOMP)
RLABR	Redirected Labor Resources (RLABR)
WPCOND	Waiting Plant Cond (WPCOND)
PLANCOMP	Planning Complete (PLANCOMP)
MATLREQD	Materials Required (MATLREQD)
MATLA	Materials Available (MATLA)
PAID	Paid (PAID)
PENDING	Pending (PENDING)
TESTING	Testing (TESTING)
RETEST	Re-test (RETEST)
REVISE	Revise and Resubmit (REVISE)
RELEASED	Released (RELEASED)
NOADDCOM	No Additional Comments (NOADDCOM)
TESTCOMP	Testing Complete (TESTCOMP)
MITIGATE	Mitigated Problem - Not Fixed (MITIGATE)
HOLD	Hold (HOLD)
WMATLALC	Waiting for Material Allocation (WMATLALC)
INCOMP	Incomplete (INCOMP)
DISAPR	Disapproved (DISAPR)
REJECTED	Rejected (REJECTED)
WMATLRVW	Waiting on Material Review by Manager (WMATLRVW)
WMATLPICK	Waiting for ALL Material to be Picked by MM (WMATLPICK)
MATLSENT	Material Sent to Lawson (MATLSENT)
WIVGT	Waiting Investigation (WIVGT)
FLDCOMP	Field Complete (FLDCOMP)
MATLREADY	Materials Are Ready (MATLREADY)
PLANINPRG	Planning In Progress (PLANINPRG)
NOACCESS	No Access (NOCCESS)
WCONTR	Waiting on Contractor (WCONTR)
ASSIGNED	Assigned (ASSIGNED)
WOTHRS	Waiting on Others (WOTHRS)
NEEDRVW	Needs Review (NEEDRVW)
WMOC	Waiting on Management of Change (WMOC)*/

 