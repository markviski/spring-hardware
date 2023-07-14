<html>
	<head>
		<title>Hardwares</title>
		<link href="styles.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	    <div class="centerdiv">
		    <h1>
			Welcome!
		    </h1>
            <#list Hardwares as hardware>
            <div class="hardware">
                <h2> ${hardware.getBrand()} ${hardware.getModel()} </h2 >
                Memory: ${hardware.getMemory()}GB </br>
                Price: &euro; ${hardware.getPrice()} </br>
                Year: ${hardware.getYear()?string?replace(",", "")} </br>
                <#list SellerInfo as sellerInfo>
                    <#if hardware.getId() == sellerInfo.getHardwareid()>
                        <p> Seller information: </br></p>
                        Seller: ${sellerInfo.getSeller()} </br>
                        Shipping Fee: ${sellerInfo.getShippingfee()} </br>
                        Quantity in stock: ${sellerInfo.getQuantity()} </br>
                        Phone: ${sellerInfo.getPhonenumber()} </br>
                    </#if>
                </#list>
            </div>
            </#list>

            <form action="logout" method='POST'>
                <button type="submit">Logout</button>
            </form>
		</div>
	</body>
</html>
