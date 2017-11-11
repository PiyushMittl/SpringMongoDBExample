function(collectionName, page, pageSize, sort, projections, criteria) {

	//print(queryParam);
	
	if (page == null || page == "" || page == -1) {
		page = 0;
	} else {
		page = parseInt(page);
	}

	if (pageSize == null || pageSize == "" || pageSize == -1) {
		pageSize = 10;
	} else {
		pageSize = parseInt(pageSize);
	}

	page = page * pageSize;

	var sortObj = {};
	if (sort != null && sort != "" && sort.length > 0) {
		sort = sort.split("#");
		sort.forEach(function(field) {
			field = field.split("@");
			sortObj[field[0]] = parseInt(field[1]);
		});
	} else {
		sortObj["_id"] = parseInt(1);
	}
	// return criteria;

	var entityObj = [];
	if (projections != null && projections != "") {
		projections.split("#").forEach(function(data) {
			projection = data;
			entityObj.push(projection);
		});
	}
	
	var projectionTemp = {};
	if (Object.keys(entityObj).length > 0) {
		entityObj.forEach(function(data) {
			projectionTemp[data] = 1;
		});
	}

	
	/*function splitArrayValue(var val) {
	    print("in fection: "+val);
	}*/
	
	
	
	var allRecordsCount = db.getCollection(collectionName).find(criteria, projectionTemp).count();
	
	var records = db.getCollection(collectionName).find(criteria, projectionTemp).skip(page).limit(pageSize).sort(sortObj).toArray();
	var returnObj = {};
	returnObj["TotalRecordCount"]=NumberInt(allRecordsCount);
	returnObj["RecordList"]=records;
	return returnObj;

}