<!DOCTYPE html>
<html>
	<head>
		<title>查看文章: ${newsitem.title} </title>
		<meta http-equiv="keywords" content="enter,your,keywords,here" />
		<meta http-equiv="description" content="A short description of this page." />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<!--<link rel="stylesheet" type="text/css" href="styles.css"> -->
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="95%" border="0" align="center" cellpadding="2" cellspacing="6">
						<tr>
							<td height="10" align="left" colspan=2></td>
						</tr>
						<tr>
							<td align="left" width="538">
								<strong>${newsitem.title}</strong>
								( ${newsitem.addtime} )
							</td>
							<td align="right">
								<a href="index.jsp">返回</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td align="left" valign=top colspan=2>
								<hr align="left" width="95%" size="1" noshade color="#cc0000">
							</td>
						</tr>
						<tr>
							<td colspan=2>${newsitem.showContent}</td>
						</tr>
					</table>
					<br>
				</td>
			</tr>
		</table>
	</body>
</html>
