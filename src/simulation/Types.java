package simulation;
//Enumeration for work order types
enum Types {FLSH5,SHCON,PM,CM,PROJ,EV,EMERG,FLTST,TAP,INV,ABAND,WQ,SHCIP,SHMTR,SHSR,SHENG,SHDEV,SHINV,FLSH4,FLSH3,FLSH1,WCONN,
			DIS,FLSH6,SHTST,SHNOT,FLSH7,SHRCH,MMADM,PSRINV,EM,PSR,COMM,FLSH10
}

/*
FLSH5	Flush (repeat or routine) Based on Test Results
SHCON	Valve Shut for Construction
PM	Preventive Maintenance
CM	Corrective Maintenance
PROJ	Project
EV	Event
EMERG	Emergency Investigation
FLTST	Flow Test
TAP	New Tap Insertion on Main
INV	Non-emergency Investigation
ABAND	Abandonment
WQ	Water Quality Issue
SHCIP	Test Shut for CIP Work
SHMTR	Valve Shut for Meter Ops
SHSR	Valve Shut for System Repair
SHENG	Valve Shut for DETS
SHDEV	Valve Shut for Developer
SHINV	Valve Shut for Investigations
FLSH4	Main Break Testing
FLSH3	Routine Monitoring
FLSH1	Customer Complaint
WCONN	New Connection to Water Main
DIS	Disinfect
FLSH6	Flush ASAP Due to Customer Complaint
SHTST	Valve Shut Test
SHNOT	Valve Shut Notification
FLSH7	Flush Blow-off Valve
SHRCH	Main Recharge
MMADM	Matl Mgmt Administrative
PSRINV	Public Space Restoration Investigation
EM	Emergency Maintenance
PSR	Public Space Restoration
COMM	Commercial Project
FLSH10	Hydrant Flush and WQ Test
*/