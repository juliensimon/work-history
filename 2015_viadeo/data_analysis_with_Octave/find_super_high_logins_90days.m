data = csvread("event_breakdown_by_member_90days.csv");
memberids = data(:,1);
logins=data(:,8);
idx = find(logins > 5000);
ids = memberids(idx);
save high_logins_90days.txt ids


