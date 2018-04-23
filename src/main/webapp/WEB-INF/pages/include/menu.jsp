<%@ page language="java" pageEncoding="UTF-8"%>

<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel 菜单用户信息 -->

		<!-- search form -->
		<!-- /.search form -->
		
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header"><sp:message code="menu"/></li>
			<li class="treeview">
				<a href="<%=path%>/baseset/list"> <i class="fa fa-cogs"></i><span><sp:message code="menu.baseset"/></span></a>
			</li>
			<li class="treeview">
				<a href="<%=path%>/group/list"> <i class="fa fa-group"></i><span><sp:message code="menu.group"/></span></a>
			</li>
			<li class="treeview">
				<a href="<%=path%>/area/list"> <i class="fa fa-history"></i><span><sp:message code="menu.history"/></span></a>
			</li>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>